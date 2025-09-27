package com.example.chubby;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.chubby.auth.AuthManager;
import com.example.chubby.auth.DishSyncManager;
import com.example.chubby.auth.RoleManager;
import com.example.chubby.model.Dish;
import com.example.chubby.model.DishDAO;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.UUID;

public class DishDetailActivity extends AppCompatActivity {

    private String dishId;
    private Dish dish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        ImageView imgDish      = findViewById(R.id.imgDishDetail);
        TextView tvName        = findViewById(R.id.tvDishName);
        TextView tvDesc        = findViewById(R.id.tvDishDescription);
        TextView tvIngredients = findViewById(R.id.tvDishIngredients);
        TextView tvRecipe      = findViewById(R.id.tvDishRecipe);
            TextView tvCookTime    = findViewById(R.id.tvDishCookTime);
            Button btnSave         = findViewById(R.id.btnSave);
            Button btnEdit         = findViewById(R.id.btnEdit);
            Button btnDelete       = findViewById(R.id.btnDelete);

        // Ẩn mặc định
        btnEdit.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);

        // Lấy role user hiện tại
        RoleManager.getCurrentUserRole(role -> {
            if ("admin".equals(role)) {
                btnEdit.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
            }
        });
        // === Nhận id từ Intent ===
        dishId = getIntent().getStringExtra("dish_id");
        if (dishId == null || dishId.trim().isEmpty()) {
            dishId = UUID.randomUUID().toString();
        }

        // === Query lại từ DB ===
        DishDAO dao = new DishDAO(this);
        dish = dao.getDishById(dishId);

        if (dish != null) {
            tvName.setText(dish.getName() != null ? dish.getName() : "");
            tvDesc.setText(dish.getDescription() != null ? dish.getDescription() : "");
            tvIngredients.setText(dish.getIngredients() != null ? dish.getIngredients() : "");
            tvRecipe.setText(dish.getRecipe() != null ? dish.getRecipe() : "");
            tvCookTime.setText("Thời gian nấu: " + dish.getCookTime() + " phút");

            if (dish.getImageData() != null && dish.getImageData().length > 0) {
                Bitmap bmp = BitmapFactory.decodeByteArray(dish.getImageData(), 0, dish.getImageData().length);
                imgDish.setImageBitmap(bmp);
            } else if (dish.getImageResId() != 0) {
                Glide.with(this)
                        .load(dish.getImageResId())
                        .transform(new RoundedCorners(24))
                        .into(imgDish);
            } else {
                imgDish.setImageResource(R.drawable.ic_avatar_placeholder);
            }
        }

        // === Kiểm tra đã lưu ===
        if (AuthManager.currentUser() != null) {
            String uid = AuthManager.currentUser().getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(uid)
                    .collection("saved_dishes")
                    .document(dishId)
                    .get()
                    .addOnSuccessListener(snap -> {
                        if (snap.exists()) {
                            btnSave.setEnabled(false);
                            btnSave.setText("Đã lưu");
                        } else {
                            btnSave.setEnabled(true);
                            btnSave.setText("Lưu");
                        }
                    })
                    .addOnFailureListener(e -> {
                        btnSave.setEnabled(true);
                        btnSave.setText("Lưu");
                    });
        } else {
            btnSave.setEnabled(true);
            btnSave.setText("Lưu");
        }

        // === Lưu vào Firestore ===
        btnSave.setOnClickListener(v -> {
            if (AuthManager.currentUser() == null) {
                Toast.makeText(this, "Vui lòng đăng nhập để lưu", Toast.LENGTH_SHORT).show();
                return;
            }

            String uid = AuthManager.currentUser().getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            HashMap<String, Object> doc = new HashMap<>();
            doc.put("id",          dishId);
            doc.put("name",        dish.getName());
            doc.put("type",        dish.getType());
            doc.put("description", dish.getDescription());
            doc.put("ingredients", dish.getIngredients());
            doc.put("recipe",      dish.getRecipe());
            doc.put("cookTime",    dish.getCookTime());
            doc.put("imageResId",  dish.getImageResId());
            doc.put("savedAt",     FieldValue.serverTimestamp());

            db.collection("users")
                    .document(uid)
                    .collection("saved_dishes")
                    .document(dishId)
                    .set(doc, SetOptions.merge())
                    .addOnSuccessListener(x -> {
                        Toast.makeText(this, "Đã lưu công thức!", Toast.LENGTH_SHORT).show();
                        btnSave.setEnabled(false);
                        btnSave.setText("Đã lưu");
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Lỗi lưu: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DishDetailActivity.this, CreateDishActivity.class);
            intent.putExtra("dish_id", dishId); // truyền id sang
            startActivity(intent);
        });
        btnDelete.setOnClickListener(v -> {
            if (dish != null) {
                new AlertDialog.Builder(this)
                        .setTitle("Xóa món ăn")
                        .setMessage("Bạn có chắc chắn muốn xóa món '" + dish.getName() + "' không?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            DishSyncManager sync= new DishSyncManager(this);
                            sync.deleteDish(dish.getId());
                            Toast.makeText(this, "Đã xóa món: " + dish.getName(), Toast.LENGTH_SHORT).show();
                            finish(); // quay lại danh sách
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            } else {
                Toast.makeText(this, "Không tìm thấy món ăn để xóa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

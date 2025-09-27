package com.example.chubby;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.chubby.auth.DishSyncManager;
import com.example.chubby.model.Dish;
import com.example.chubby.model.DishDAO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreateDishActivity extends AppCompatActivity {

    private ImageView imgDish;
    private EditText edtName, edtDescription, edtIngredients, edtInstructions, edtCookingTime;
    private Button btnSelectImage, btnSaveDish;

    private Uri selectedImageUri = null;
    private DishDAO dishDAO;
    private String dishId = null;
    private Dish currentDish = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dish);

        // Init DAO
        dishDAO = new DishDAO(this);

        // Ánh xạ view
        imgDish = findViewById(R.id.imgDish);
        edtName = findViewById(R.id.edtName);
        edtDescription = findViewById(R.id.edtDescription);
        edtIngredients = findViewById(R.id.edtIngredients);
        edtInstructions = findViewById(R.id.edtInstructions);
        edtCookingTime = findViewById(R.id.edtCookingTime);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSaveDish = findViewById(R.id.btnSaveDish);

        // Nhận dishId từ Intent nếu có (edit mode)
        dishId = getIntent().getStringExtra("dish_id");
        if (dishId != null) {
            currentDish = dishDAO.getDishById(dishId);
            if (currentDish != null) {
                // Hiển thị dữ liệu cũ lên form
                edtName.setText(currentDish.getName());
                edtDescription.setText(currentDish.getDescription());
                edtIngredients.setText(currentDish.getIngredients());
                edtInstructions.setText(currentDish.getRecipe());
                edtCookingTime.setText(String.valueOf(currentDish.getCookTime()));

                if (currentDish.getImageData() != null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(
                            currentDish.getImageData(), 0, currentDish.getImageData().length);
                    imgDish.setImageBitmap(bmp);
                }
                btnSaveDish.setText("Cập nhật"); // đổi label nút
            }
        }

        // Chọn ảnh từ gallery
        btnSelectImage.setOnClickListener(v -> openGallery());

        // Lưu món ăn
        btnSaveDish.setOnClickListener(v -> saveDish());
    }

    // ================== Mở Gallery chọn ảnh ================== //
    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        imgDish.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Không thể load ảnh", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    // ================== Convert ảnh từ ImageView -> byte[] ================== //
    private byte[] imageViewToByte(ImageView imageView) {
        if (imageView.getDrawable() == null) return null;
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    // ================== Lưu món ăn ================== //
    private void saveDish() {
        try {
            String name = edtName.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();
            String ingredients = edtIngredients.getText().toString().trim();
            String instructions = edtInstructions.getText().toString().trim();
            String cookTimeStr = edtCookingTime.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) ||
                    TextUtils.isEmpty(ingredients) || TextUtils.isEmpty(instructions) ||
                    TextUtils.isEmpty(cookTimeStr)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int cookTime;
            try {
                cookTime = Integer.parseInt(cookTimeStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Thời gian nấu phải là số", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy ảnh từ ImageView -> byte[]
            byte[] imageBytes = imageViewToByte(imgDish);

            // Nếu chưa chọn ảnh thì dùng placeholder mặc định
            if (imageBytes == null) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_avatar_placeholder);
                if (drawable instanceof BitmapDrawable) {
                    Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imageBytes = stream.toByteArray();
                } else {
                    Log.e("CreateDishActivity", "Placeholder drawable null hoặc không phải BitmapDrawable");
                }
            }

            DishSyncManager sync = new DishSyncManager(this);

            if (dishId == null) {
                // === Thêm mới ===
                Dish newDish = new Dish(null, name, imageBytes, "Featured",
                        description, ingredients, instructions, cookTime);

                // ✅ Set thời gian tạo
                newDish.setCreatedAt(System.currentTimeMillis());

                sync.addDish(newDish);
                Toast.makeText(this, "Đã thêm món: " + name, Toast.LENGTH_SHORT).show();
            } else {
                // === Cập nhật ===
                currentDish.setName(name);
                currentDish.setDescription(description);
                currentDish.setIngredients(ingredients);
                currentDish.setRecipe(instructions);
                currentDish.setCookTime(cookTime);
                currentDish.setImageData(imageBytes);

                sync.updateDish(currentDish);
                Toast.makeText(this, "Đã cập nhật món: " + name, Toast.LENGTH_SHORT).show();
            }

            finish();

        } catch (Exception e) {
            Log.e("CreateDishActivity", "Lỗi khi lưu món ăn", e);
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

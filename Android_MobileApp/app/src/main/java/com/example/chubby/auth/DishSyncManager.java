package com.example.chubby.auth;

import android.content.Context;
import android.util.Log;

import com.example.chubby.model.Dish;
import com.example.chubby.model.DishDAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DishSyncManager {
    private final DishDAO dishDAO;
    private final FirebaseFirestore db;

    public DishSyncManager(Context context) {
        this.dishDAO = new DishDAO(context);
        this.db = FirebaseFirestore.getInstance();
    }

    // ================== Thêm món ================== //
    public void addDish(Dish dish) {
        try {
            // Nếu dish chưa có id thì sinh UUID
            if (dish.getId() == null || dish.getId().trim().isEmpty()) {
                dish.setId(UUID.randomUUID().toString());
            }

            // Nếu chưa có createdAt thì set thời gian hiện tại
            if (dish.getCreatedAt() == 0) {
                dish.setCreatedAt(System.currentTimeMillis());
            }

            // Lưu local (SQLite)
            dishDAO.insertDish(dish);

            // Lưu cloud (Firestore)
            Map<String, Object> doc = dishToMap(dish);
            db.collection("dishes")
                    .document(dish.getId())
                    .set(doc, SetOptions.merge())
                    .addOnSuccessListener(a -> Log.d("Sync", "Đã đồng bộ thêm món lên Firestore: " + dish.getName()))
                    .addOnFailureListener(e -> Log.e("Sync", "Lỗi sync thêm: " + e.getMessage()));
        } catch (Exception e) {
            Log.e("Sync", "Lỗi addDish local: ", e);
        }
    }

    // ================== Cập nhật món ================== //
    public void updateDish(Dish dish) {
        try {
            if (dish.getId() == null || dish.getId().trim().isEmpty()) {
                Log.e("Sync", "Dish chưa có id, không thể cập nhật Firestore");
                return;
            }

            // Update local
            dishDAO.updateDish(dish);

            // Update cloud
            Map<String, Object> doc = dishToMap(dish);
            db.collection("dishes")
                    .document(dish.getId())
                    .set(doc, SetOptions.merge())
                    .addOnSuccessListener(a -> Log.d("Sync", "Đã đồng bộ cập nhật lên Firestore: " + dish.getName()))
                    .addOnFailureListener(e -> Log.e("Sync", "Lỗi sync update: " + e.getMessage()));
        } catch (Exception e) {
            Log.e("Sync", "Lỗi updateDish local: ", e);
        }
    }

    // ================== Xóa món ================== //
    public void deleteDish(String dishId) {
        try {
            if (dishId == null || dishId.trim().isEmpty()) {
                Log.e("Sync", "DishId null, không thể xóa");
                return;
            }

            // Xóa local
            dishDAO.deleteDish(dishId);

            // Xóa trên Firestore collection "dishes"
            db.collection("dishes")
                    .document(dishId)
                    .delete()
                    .addOnSuccessListener(a -> {
                        Log.d("Sync", "Đã đồng bộ xóa lên Firestore: " + dishId);

                        // ✅ Xóa luôn trong saved_dishes của user hiện tại
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        db.collection("users")
                                .document(uid)
                                .collection("saved_dishes")
                                .document(dishId)
                                .delete()
                                .addOnSuccessListener(v -> Log.d("Sync", "Đã xóa dishId trong saved_dishes của user " + uid))
                                .addOnFailureListener(e ->
                                        Log.e("Sync", "Lỗi khi xóa saved_dishes: " + e.getMessage()));
                    })
                    .addOnFailureListener(e -> Log.e("Sync", "Lỗi sync delete: " + e.getMessage()));

        } catch (Exception e) {
            Log.e("Sync", "Lỗi deleteDish local: ", e);
        }
    }

    // ================== Chuyển Dish -> Map để lưu Firestore ================== //
    private Map<String, Object> dishToMap(Dish dish) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", dish.getId());
        map.put("name", dish.getName());
        map.put("description", dish.getDescription());
        map.put("ingredients", dish.getIngredients());
        map.put("recipe", dish.getRecipe());
        map.put("cookTime", dish.getCookTime());
        map.put("type", dish.getType());
        map.put("imageResId", dish.getImageResId());
        map.put("createdAt", dish.getCreatedAt()); // ✅ thêm thời gian tạo
        return map;
    }
}

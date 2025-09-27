// data/SavedRepository.java
package com.example.chubby.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chubby.auth.AuthManager;
import com.example.chubby.model.Dish;
import com.example.chubby.model.DishDAO;
import com.example.chubby.model.SavedDish;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SavedRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final DishDAO dishDAO;

    // Truyền Context để DishDAO dùng SQLite
    public SavedRepository(Context context) {
        this.dishDAO = new DishDAO(context);
    }

    private CollectionReference col() {
        String uid = (AuthManager.isLoggedIn() && AuthManager.currentUser() != null)
                ? AuthManager.currentUser().getUid() : "null";
        Log.d("SavedRepository", "col() -> users/" + uid + "/saved_dishes");
        return db.collection("users").document(uid).collection("saved_dishes");
    }

    // Lưu document lên Firestore
    public Task<Void> save(@NonNull SavedDish d) {
        if (d.id == null) d.id = d.name;  // ⚡ bạn nên sửa: UUID.randomUUID().toString()
        if (d.savedAt == null) d.savedAt = Timestamp.now();
        Log.d("SavedRepository", "save() id=" + d.id + " name=" + d.name);
        return col().document(d.id).set(d);
    }

    // Query Firestore thô (nếu muốn dùng snapshot listener)
    public CollectionReference queryMine() {
        CollectionReference ref = col();
        Log.d("SavedRepository", "queryMine() -> " + ref.getPath());
        return ref;
    }

    // ================== FIXED ================== //
    // Lấy toàn bộ SavedDish từ Firestore rồi join dữ liệu từ SQLite bằng id
    public void getSavedDishes(OnSavedDishesLoaded listener) {
        col().get().addOnSuccessListener(query -> {
            List<Dish> list = new ArrayList<>();
            for (DocumentSnapshot doc : query.getDocuments()) {
                SavedDish saved = doc.toObject(SavedDish.class);
                if (saved == null) continue;

                Dish localDish = null;
                if (saved.id != null) {
                    // Ưu tiên tìm theo id
                    localDish = dishDAO.getDishById(saved.id);
                }
                if (localDish == null && saved.name != null) {
                    // fallback nếu chưa có id, tìm theo name
                    localDish = dishDAO.getDishByName(saved.name);
                }

                if (localDish != null) {
                    list.add(localDish);
                } else {
                    // fallback nếu SQLite chưa có → tạo object text-only
                    list.add(new Dish(
                            saved.id,
                            saved.name,
                            null, // chưa có ảnh
                            null,
                            null,
                            saved.ingredients,
                            saved.recipe,
                            saved.cookTime != null ? saved.cookTime : 0
                    ));
                }
            }
            listener.onLoaded(list);
        }).addOnFailureListener(e -> {
            Log.e("SavedRepository", "getSavedDishes() error", e);
            listener.onLoaded(new ArrayList<>());
        });
    }

    // Callback interface
    public interface OnSavedDishesLoaded {
        void onLoaded(List<Dish> dishes);
    }
}

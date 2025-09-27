package com.example.chubby.data;

import android.content.Context;
import android.util.Log;

import com.example.chubby.R;
import com.example.chubby.auth.DishSyncManager;
import com.example.chubby.model.Dish;
import com.example.chubby.model.DishDAO;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class DishRepository {
    private final DishDAO dishDAO;
    private final FirebaseFirestore db;
    private final DishSyncManager syncManager;

    public DishRepository(Context context) {
        this.dishDAO = new DishDAO(context);
        this.db = FirebaseFirestore.getInstance();
        this.syncManager = new DishSyncManager(context);
    }

    // ================== Lấy tất cả ================== //
    public void getAllDishes(Consumer<List<Dish>> callback) {
        dishDAO.insertSampleData();

        db.collection("dishes")
                .orderBy("createdAt", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Dish> finalList = new ArrayList<>();

                    // Push sample lên Firebase
                    List<Dish> samples = dishDAO.getAllDishes();
                    for (Dish d : samples) {
                        if (d.getId() != null && d.getId().startsWith("sample_")) {
                            syncManager.addDish(d);
                        }
                    }

                    // Load từ Firestore
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Dish d = doc.toObject(Dish.class);
                        if (d == null) continue;

                        // Ghép ảnh từ SQLite
                        Dish localDish = dishDAO.getDishById(d.getId());
                        if (localDish != null) {
                            if (localDish.getImageData() != null) {
                                d.setImageData(localDish.getImageData());
                            } else if (localDish.getImageResId() != 0) {
                                d.setImageResId(localDish.getImageResId());
                            } else {
                                d.setImageResId(R.drawable.ic_avatar_placeholder);
                            }
                        } else {
                            if (d.getImageResId() == 0) {
                                d.setImageResId(R.drawable.ic_avatar_placeholder);
                            }
                        }

                        finalList.add(d);
                    }

                    Collections.sort(finalList, Comparator.comparingLong(Dish::getCreatedAt));
                    callback.accept(finalList);
                })
                .addOnFailureListener(e -> {
                    Log.e("DishRepository", "Lỗi load dishes Firestore: " + e.getMessage());
                    callback.accept(new ArrayList<>());
                });
    }


    // ================== Search theo tên ================== //
    public void searchByName(String keyword, Consumer<List<Dish>> callback) {
        db.collection("dishes")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Dish> out = new ArrayList<>();
                    String kwLower = keyword.toLowerCase(Locale.ROOT);

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Dish d = doc.toObject(Dish.class);
                        if (d != null && d.getName() != null &&
                                d.getName().toLowerCase(Locale.ROOT).contains(kwLower)) {
                            Dish localDish = dishDAO.getDishById(d.getId());
                            if (localDish != null && localDish.getImageData() != null) {
                                d.setImageData(localDish.getImageData());
                            } else if (localDish != null && localDish.getImageResId() != 0) {
                                d.setImageResId(localDish.getImageResId());
                            } else {
                                d.setImageResId(R.drawable.ic_avatar_placeholder);
                            }
                            out.add(d);
                        }
                    }

                    Collections.sort(out, Comparator.comparingLong(Dish::getCreatedAt));
                    callback.accept(out);
                })
                .addOnFailureListener(e -> {
                    Log.e("DishRepository", "Lỗi searchByName Firestore: " + e.getMessage());
                    callback.accept(new ArrayList<>());
                });
    }

    // ================== Search theo nhiều nguyên liệu ================== //
    public void searchByIngredients(String queryCsv, Consumer<List<Dish>> callback) {
        db.collection("dishes")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Dish> out = new ArrayList<>();

                    List<String> needs = new ArrayList<>();
                    if (queryCsv != null) {
                        for (String part : queryCsv.split(",")) {
                            String n = part.trim().toLowerCase(Locale.ROOT);
                            if (!n.isEmpty()) needs.add(n);
                        }
                    }

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Dish d = doc.toObject(Dish.class);
                        if (d == null || d.getIngredients() == null) continue;

                        String ingredientsLower = d.getIngredients().toLowerCase(Locale.ROOT);

                        boolean allMatch = true;
                        for (String need : needs) {
                            if (!ingredientsLower.contains(need)) {
                                allMatch = false;
                                break;
                            }
                        }

                        if (allMatch) {
                            Dish localDish = dishDAO.getDishById(d.getId());
                            if (localDish != null && localDish.getImageData() != null) {
                                d.setImageData(localDish.getImageData());
                            } else if (localDish != null && localDish.getImageResId() != 0) {
                                d.setImageResId(localDish.getImageResId());
                            } else {
                                d.setImageResId(R.drawable.ic_avatar_placeholder);
                            }
                            out.add(d);
                        }
                    }

                    Collections.sort(out, Comparator.comparingLong(Dish::getCreatedAt));
                    callback.accept(out);
                })
                .addOnFailureListener(e -> {
                    Log.e("DishRepository", "Lỗi searchByIngredients Firestore: " + e.getMessage());
                    callback.accept(new ArrayList<>());
                });
    }
}

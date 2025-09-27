package com.example.chubby.model;

import java.util.UUID;

public class Dish {
    private String id;          // ✅ id duy nhất cho mỗi món ăn
    private String name;

    // Có thể dùng 1 trong 2 kiểu ảnh:
    private byte[] imageData;   // ảnh dạng byte[] (gallery, lưu SQLite)
    private int imageResId;     // ảnh resource id (sample data, drawable)

    private String type;
    private String description;
    private String ingredients;
    private String recipe;
    private int cookTime;

    private long createdAt;     // ✅ thời gian tạo (timestamp)

    // ============== Constructors ==============

    // Constructor 1: ảnh từ gallery (BLOB)
    public Dish(String id, String name, byte[] imageData, String type,
                String description, String ingredients,
                String recipe, int cookTime) {
        this.id = (id == null || id.trim().isEmpty()) ? UUID.randomUUID().toString() : id;
        this.name = name;
        this.imageData = imageData;
        this.type = type;
        this.description = description;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.cookTime = cookTime;
        this.createdAt = System.currentTimeMillis(); // mặc định thời gian tạo
    }

    // Constructor 2: ảnh từ resource (drawable id)
    public Dish(String id, String name, int imageResId, String type,
                String description, String ingredients,
                String recipe, int cookTime) {
        this.id = (id == null || id.trim().isEmpty()) ? UUID.randomUUID().toString() : id;
        this.name = name;
        this.imageResId = imageResId;
        this.type = type;
        this.description = description;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.cookTime = cookTime;
        this.createdAt = System.currentTimeMillis();
    }

    // Constructor rỗng (nếu cần tạo object rỗng để set từng field)
    public Dish() {
        this.id = UUID.randomUUID().toString(); // luôn có id mặc định
        this.createdAt = System.currentTimeMillis();
    }

    // ============== Getter & Setter ==============
    public String getId() {
        return id;
    }
    public void setId(String id) {
        // nếu truyền null thì sinh UUID mới
        this.id = (id == null || id.trim().isEmpty()) ? UUID.randomUUID().toString() : id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImageData() {
        return imageData;
    }
    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public int getImageResId() {
        return imageResId;
    }
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe() {
        return recipe;
    }
    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public int getCookTime() {
        return cookTime;
    }
    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public long getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}

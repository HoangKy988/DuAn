package com.example.chubby.model;

public class SavedDish {
    public String id;
    public String name;
    public String imageUrl;
    public Integer imageResId;
    public Integer cookTime;
    public String ingredients;
    public String recipe;
    public com.google.firebase.Timestamp savedAt;

    public SavedDish() {}
}

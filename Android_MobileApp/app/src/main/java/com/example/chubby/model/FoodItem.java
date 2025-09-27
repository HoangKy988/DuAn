package com.example.chubby.model;

public class FoodItem {
    private String name;
    private int kcalPer100g;

    public FoodItem(String name, int kcalPer100g) {
        this.name = name;
        this.kcalPer100g = kcalPer100g;
    }

    public String getName() {
        return name;
    }

    public int getKcalPer100g() {
        return kcalPer100g;
    }
}


package com.example.chubby.model;

public class User {
    public String displayName;
    public String email;
    public String role; // "user" | "admin"

    public User() {}
    public User(String displayName, String email, String role) {
        this.displayName = displayName;
        this.email = email;
        this.role = role;
    }
}

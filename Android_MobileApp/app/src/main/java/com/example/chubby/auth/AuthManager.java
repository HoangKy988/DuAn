package com.example.chubby.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * AuthManager: quản lý trạng thái đăng nhập của FirebaseAuth.
 */
public class AuthManager {
    private static final FirebaseAuth auth = FirebaseAuth.getInstance();

    /** Lấy user hiện tại (có thể null nếu chưa login). */
    public static FirebaseUser currentUser() {
        return auth.getCurrentUser();
    }

    /** Kiểm tra đã đăng nhập chưa. */
    public static boolean isLoggedIn() {
        return currentUser() != null;
    }

    /** Đăng xuất, đồng thời reset cache RoleManager. */
    public static void signOut() {
        auth.signOut();
        RoleManager.invalidateCache();  // ✅ clear cache role khi logout
    }
}

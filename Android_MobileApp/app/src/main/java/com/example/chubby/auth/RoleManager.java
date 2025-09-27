package com.example.chubby.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

/**
 * RoleManager: lấy role của user từ Firestore.
 * Trường "role" trong users/{uid} có thể là:
 *   - "user" (mặc định)
 *   - "admin"
 */
public class RoleManager {

    public interface Callback {
        void onResult(@Nullable String role);
    }

    /** Cache theo người dùng hiện tại */
    private static @Nullable String cachedRole = null;
    private static @Nullable String cachedUid  = null;

    /** Lấy role hiện tại từ Firestore (cache theo UID, ưu tiên server). */
    public static void getCurrentUserRole(@NonNull final Callback cb) {
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if (u == null) { cb.onResult(null); return; }

        final String uid = u.getUid();

        // Trả từ cache NẾU cache thuộc về đúng UID hiện tại
        if (cachedRole != null && uid.equals(cachedUid)) {
            cb.onResult(cachedRole);
            return;
        }

        // Query Firestore: users/{uid}.role — ưu tiên SERVER để tránh dữ liệu cũ
        FirebaseFirestore.getInstance()
                .collection("users").document(uid)
                .get(Source.SERVER)
                .addOnSuccessListener((DocumentSnapshot snapshot) -> {
                    String role = snapshot != null ? snapshot.getString("role") : null;
                    cachedUid  = uid;
                    cachedRole = role;
                    cb.onResult(role);
                })
                .addOnFailureListener(e -> cb.onResult(null));
    }

    /** Reset cache — gọi khi logout hoặc khi bạn muốn ép đọc lại từ server. */
    public static void invalidateCache() {
        cachedRole = null;
        cachedUid  = null;
    }
}

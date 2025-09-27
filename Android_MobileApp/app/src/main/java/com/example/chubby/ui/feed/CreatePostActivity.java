package com.example.chubby.ui.feed;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.chubby.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatePostActivity extends AppCompatActivity {

    private EditText edtContent;
    private MaterialButton btnPost;       // ✅ sửa thành MaterialButton
    private MaterialButton btnPickImage;  // ✅ sửa thành MaterialButton
    private ImageView imgPreview;

    private final List<Uri> selectedUris = new ArrayList<>();

    @Nullable
    private String editingPostId = null; // != null => chế độ sửa bài

    private ActivityResultLauncher<String> pickImageLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        edtContent   = findViewById(R.id.edtContent);
        btnPost      = findViewById(R.id.btnPost);
        btnPickImage = findViewById(R.id.btnPickImage);
        imgPreview   = findViewById(R.id.imgPreview);

        // Nếu mở bằng chế độ sửa
        editingPostId = getIntent().getStringExtra("EXTRA_POST_ID");
        String existingContent = getIntent().getStringExtra("EXTRA_POST_CONTENT");

        if (!TextUtils.isEmpty(existingContent)) {
            edtContent.setText(existingContent);
        }
        if (editingPostId != null) {
            btnPost.setText(R.string.action_update_post); // "Cập nhật"
        }

        // Launcher chọn ảnh
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedUris.clear();
                        selectedUris.add(uri);
                        Glide.with(this).load(uri).into(imgPreview);
                        imgPreview.setVisibility(ImageView.VISIBLE);
                    }
                }
        );

        btnPickImage.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        btnPost.setOnClickListener(v -> doPost());
    }

    private void doPost() {
        String content = edtContent.getText().toString().trim();

        // 🔹 Chặn bài rỗng (không nội dung, không ảnh)
        if (TextUtils.isEmpty(content) && selectedUris.isEmpty()) {
            Toast.makeText(this, "Bạn phải nhập nội dung hoặc chọn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if (u == null) {
            Toast.makeText(this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }
        String uid = u.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Nếu đang sửa bài → chỉ update content
        if (editingPostId != null) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("content", content);
            updates.put("updatedAt", FieldValue.serverTimestamp());

            db.collection("posts").document(editingPostId)
                    .update(updates)
                    .addOnSuccessListener(ref -> {
                        Toast.makeText(this, "Đã cập nhật bài viết", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
            return;
        }

        // ==== Tạo mới bài viết: lấy name + avatar từ users/{uid} ====
        db.collection("users").document(uid).get()
                .addOnSuccessListener((DocumentSnapshot doc) -> {
                    String nameInUsers   = (doc != null) ? doc.getString("name") : null;
                    String avatarInUsers = (doc != null) ? doc.getString("avatarUrl") : null;

                    // Fallback tên nếu user chưa đặt
                    String displayName = !TextUtils.isEmpty(nameInUsers) ? nameInUsers : u.getDisplayName();
                    if (TextUtils.isEmpty(displayName)) {
                        String email = u.getEmail();
                        if (!TextUtils.isEmpty(email) && email.contains("@")) {
                            displayName = email.substring(0, email.indexOf('@'));
                        } else {
                            displayName = "Người dùng";
                        }
                    }

                    // Chuyển Uri thành String để lưu Firestore
                    List<String> uriStrings = new ArrayList<>();
                    for (Uri uri : selectedUris) {
                        uriStrings.add(uri.toString());
                    }

                    // Chuẩn bị dữ liệu bài post
                    Map<String, Object> data = new HashMap<>();
                    data.put("content", content);

                    data.put("authorId", uid);
                    data.put("authorEmail", u.getEmail());
                    data.put("authorName", displayName);
                    data.put("authorAvatarUrl", avatarInUsers);

                    data.put("imageUrls", uriStrings); // ✅ lưu list URI local
                    data.put("likeCount", 0);
                    data.put("commentCount", 0);
                    data.put("createdAt", FieldValue.serverTimestamp());

                    // Ghi post mới
                    db.collection("posts")
                            .add(data)
                            .addOnSuccessListener(ref -> {
                                Toast.makeText(this, "Đăng bài thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                            );
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi lấy thông tin người dùng: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}

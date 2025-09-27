package com.example.chubby;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private com.google.android.material.textfield.TextInputEditText edtName;
    private ImageView imgPreview;
    private Button btnChooseImage, btnSave;

    private FirebaseUser currentUser;
    private Uri imageUri; // ảnh được chọn từ thiết bị

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edtName = findViewById(R.id.edtName);
        imgPreview = findViewById(R.id.imgPreview);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSave = findViewById(R.id.btnSave);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load dữ liệu hiện có từ Firestore
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUser.getUid())
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        String name = doc.getString("name");
                        String avatarUrl = doc.getString("avatarUrl");
                        if (!TextUtils.isEmpty(name)) edtName.setText(name);
                        if (!TextUtils.isEmpty(avatarUrl)) {
                            Glide.with(this).load(Uri.parse(avatarUrl)).circleCrop().into(imgPreview);
                        }
                    }
                });

        btnChooseImage.setOnClickListener(v -> openFileChooser());
        btnSave.setOnClickListener(v -> saveProfile());
    }

    /** Mở gallery chọn ảnh */
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    /** Nhận ảnh chọn từ gallery */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).circleCrop().into(imgPreview);
        }
    }

    /** Lưu thông tin hồ sơ và update toàn bộ post của user */
    private void saveProfile() {
        final String name = edtName.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Tên không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String uid = currentUser.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String avatarUrl = (imageUri != null) ? imageUri.toString() : null;

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        if (avatarUrl != null) {
            updates.put("avatarUrl", avatarUrl);
        }

        db.collection("users").document(uid)
                .update(updates)
                .addOnSuccessListener(unused -> {
                    updatePostsWithNewProfile(uid, name, avatarUrl);
                    Toast.makeText(this, "Cập nhật hồ sơ thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    /** Update tất cả post cũ của user */
    private void updatePostsWithNewProfile(String uid, String newName, @Nullable String newAvatarUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("posts")
                .whereEqualTo("authorId", uid)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Map<String, Object> postUpdates = new HashMap<>();
                        postUpdates.put("authorName", newName);
                        if (newAvatarUrl != null) {
                            postUpdates.put("authorAvatarUrl", newAvatarUrl); // dùng trong FeedAdapter
                        }
                        db.collection("posts").document(doc.getId()).update(postUpdates);
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Không thể update bài viết: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}

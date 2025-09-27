package com.example.chubby.ui.comment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chubby.R;
import com.example.chubby.data.Comment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    private static final String TAG = "CommentActivity";

    private RecyclerView rvComments;
    private EditText edtComment;
    private ImageButton btnSend;
    private CommentAdapter adapter;
    private FirebaseFirestore db;
    private String postId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // 🔹 Setup toolbar có nút back
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        rvComments = findViewById(R.id.rvComments);
        edtComment = findViewById(R.id.edtComment);
        btnSend    = findViewById(R.id.btnSend);

        db = FirebaseFirestore.getInstance();

        postId = getIntent().getStringExtra("EXTRA_POST_ID");
        Log.d(TAG, "EXTRA_POST_ID = " + postId);
        if (TextUtils.isEmpty(postId)) {
            Log.e(TAG, "❌ postId null => finish()");
            finish();
            return;
        }

        adapter = new CommentAdapter();
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(adapter);

        // 🔹 Load realtime comments
        db.collection("posts").document(postId)
                .collection("comments")
                .orderBy("createdAt", Query.Direction.ASCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.e(TAG, "❌ Firestore error", e);
                        return;
                    }
                    List<Comment> list = new ArrayList<>();
                    if (snapshots != null) {
                        for (DocumentSnapshot d : snapshots.getDocuments()) {
                            try {
                                Comment c = new Comment();
                                c.setId(d.getId());
                                c.setAuthorId(d.getString("authorId"));
                                c.setAuthorName(d.getString("authorName"));
                                c.setAuthorAvatarUrl(d.getString("authorAvatarUrl")); // 🔹 map avatar
                                c.setContent(d.getString("content"));

                                Timestamp ts = d.getTimestamp("createdAt");
                                if (ts != null) {
                                    c.setCreatedAt(ts.toDate().getTime());
                                }
                                list.add(c);

                                Log.d(TAG, "✅ Comment loaded: " + c.getAuthorName() + " - " + c.getContent());
                            } catch (Exception ex) {
                                Log.e(TAG, "❌ Mapping comment error", ex);
                            }
                        }
                    }
                    adapter.submitList(list);
                });

        btnSend.setOnClickListener(v -> sendComment());
    }

    private void sendComment() {
        String text = edtComment.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            Log.w(TAG, "⚠️ Không nhập nội dung comment");
            return;
        }

        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if (u == null) {
            Toast.makeText(this, "Bạn cần đăng nhập", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "❌ User chưa đăng nhập");
            return;
        }

        DocumentReference postRef = db.collection("posts").document(postId);
        DocumentReference commentRef = postRef.collection("comments").document();

        // 🔹 Lấy avatar từ Firestore users collection
        db.collection("users").document(u.getUid())
                .get()
                .addOnSuccessListener(doc -> {
                    String avatarUrl = (doc != null) ? doc.getString("avatarUrl") : null;

                    Map<String, Object> data = new HashMap<>();
                    data.put("authorId", u.getUid());
                    data.put("authorName", u.getDisplayName() != null ? u.getDisplayName() : "Ẩn danh");
                    data.put("authorAvatarUrl", avatarUrl); // 🔹 lưu avatar
                    data.put("content", text);
                    data.put("createdAt", FieldValue.serverTimestamp());

                    commentRef.set(data).addOnSuccessListener(a -> {
                        postRef.update("commentCount", FieldValue.increment(1));
                        edtComment.setText("");
                        Log.d(TAG, "✅ Gửi comment thành công");
                    }).addOnFailureListener(e ->
                            Log.e(TAG, "❌ Lỗi gửi comment", e)
                    );
                })
                .addOnFailureListener(e -> Log.e(TAG, "❌ Không load được avatar", e));
    }
}

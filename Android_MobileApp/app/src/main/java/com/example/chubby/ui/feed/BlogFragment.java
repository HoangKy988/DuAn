package com.example.chubby.ui.feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.chubby.R;
import com.example.chubby.auth.RoleManager;
import com.example.chubby.data.Post;
import com.example.chubby.ui.comment.CommentActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlogFragment extends Fragment {

    private RecyclerView rvFeed;
    private SwipeRefreshLayout swipe;
    private FloatingActionButton fabNewPost;
    private MaterialToolbar toolbar;

    private FeedAdapter adapter;
    private FirebaseFirestore db;
    private ListenerRegistration registration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar     = view.findViewById(R.id.toolbar);
        rvFeed      = view.findViewById(R.id.rvFeed);
        swipe       = view.findViewById(R.id.swipe);
        fabNewPost  = view.findViewById(R.id.fabNewPost);

        db = FirebaseFirestore.getInstance();

        // Lấy user hiện tại & uid
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserId = (u != null ? u.getUid() : null);

        // Adapter + listener cho item actions
        adapter = new FeedAdapter(currentUserId == null ? "" : currentUserId, new FeedAdapter.Listener() {
            @Override public void onEdit(@NonNull Post post) {
                if (post.getId() == null) return;
                Intent intent = new Intent(requireContext(), CreatePostActivity.class);
                intent.putExtra("EXTRA_POST_ID", post.getId());
                intent.putExtra("EXTRA_POST_CONTENT", post.getContent());
                startActivity(intent);
            }

            @Override public void onDelete(@NonNull Post post) {
                if (post.getId() == null) return;
                db.collection("posts").document(post.getId())
                        .delete()
                        .addOnSuccessListener(v -> Toast.makeText(requireContext(), "Đã xóa", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override public void onLike(@NonNull Post post) {
                if (post.getId() == null) return;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Toast.makeText(requireContext(), "Bạn cần đăng nhập", Toast.LENGTH_SHORT).show();
                    return;
                }
                String uid = user.getUid();

                DocumentReference postRef = db.collection("posts").document(post.getId());
                DocumentReference likeRef = postRef.collection("likes").document(uid);

                likeRef.get().addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        // Đã like rồi -> bỏ like
                        likeRef.delete();
                        postRef.update("likeCount", FieldValue.increment(-1));
                    } else {
                        // Chưa like -> thêm like
                        Map<String, Object> data = new HashMap<>();
                        data.put("likedAt", FieldValue.serverTimestamp());
                        likeRef.set(data);
                        postRef.update("likeCount", FieldValue.increment(1));
                    }
                });
            }

            @Override public void onComment(@NonNull Post post) {
                if (post.getId() == null) return;
                Intent intent = new Intent(requireContext(), CommentActivity.class);
                intent.putExtra("EXTRA_POST_ID", post.getId());
                startActivity(intent);
            }


            @Override public void onImageClick(@NonNull Post post, int index) {
                Toast.makeText(requireContext(), "Ảnh " + (index + 1), Toast.LENGTH_SHORT).show();
            }

            @Override public void onAvatarClick(@NonNull Post post) { }

            @Override public void onAuthorClick(@NonNull Post post) { }
        });

        rvFeed.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvFeed.setAdapter(adapter);

        // Lấy role và truyền vào adapter (để bật/tắt Edit/Delete theo quyền)
        RoleManager.getCurrentUserRole(role -> {
            adapter.setCurrentRole(role);
        });

        // Pull-to-refresh
        if (swipe != null) {
            swipe.setOnRefreshListener(this::fetchOnce);
        }

        // Nút Thêm bài viết
        fabNewPost.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), CreatePostActivity.class))
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        startRealtime();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopRealtime();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopRealtime();
        if (rvFeed != null) rvFeed.setAdapter(null);
    }

    /* ====== Firestore ====== */

    private Query baseQuery() {
        return db.collection("posts")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(100);
    }

    private void startRealtime() {
        stopRealtime();
        registration = baseQuery().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (!isAdded()) return;
                if (e != null) {
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Post> list = new ArrayList<>();
                if (snapshots != null) {
                    for (DocumentSnapshot d : snapshots.getDocuments()) {
                        Post p = mapDocToPost(d);
                        if (p != null) list.add(p);
                    }
                }
                adapter.submitList(list);
            }
        });
    }

    private void stopRealtime() {
        if (registration != null) {
            registration.remove();
            registration = null;
        }
        if (swipe != null) swipe.setRefreshing(false);
    }

    /** Refresh thủ công một lần */
    private void fetchOnce() {
        baseQuery().get()
                .addOnSuccessListener(snapshots -> {
                    List<Post> list = new ArrayList<>();
                    for (DocumentSnapshot d : snapshots.getDocuments()) {
                        Post p = mapDocToPost(d);
                        if (p != null) list.add(p);
                    }
                    adapter.submitList(list);
                    if (swipe != null) swipe.setRefreshing(false);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    if (swipe != null) swipe.setRefreshing(false);
                });
    }

    private @Nullable Post mapDocToPost(@NonNull DocumentSnapshot d) {
        try {
            Post p = new Post();
            p.setId(d.getId());
            p.setAuthorId(d.getString("authorId"));
            p.setAuthorName(d.getString("authorName"));
            p.setAuthorAvatarUrl(d.getString("authorAvatarUrl"));
            p.setContent(d.getString("content"));

            Timestamp ts = d.getTimestamp("createdAt");
            long createdMs = ts != null ? ts.toDate().getTime() : 0L;
            p.setCreatedAt(createdMs);

            @SuppressWarnings("unchecked")
            List<String> imgs = (List<String>) d.get("imageUrls");
            p.setImageUrls(imgs);

            Long like = d.getLong("likeCount");
            p.setLikeCount(like != null ? like.intValue() : 0);

            Long cmt = d.getLong("commentCount");
            p.setCommentCount(cmt != null ? cmt.intValue() : 0);

            return p;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

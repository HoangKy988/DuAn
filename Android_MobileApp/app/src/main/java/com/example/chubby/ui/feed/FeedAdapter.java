package com.example.chubby.ui.feed;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chubby.R;
import com.example.chubby.data.Post;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FeedAdapter extends ListAdapter<Post, FeedAdapter.VH> {

    public interface Listener {
        void onEdit(@NonNull Post post);
        void onDelete(@NonNull Post post);
        void onLike(@NonNull Post post);
        void onComment(@NonNull Post post);
        void onImageClick(@NonNull Post post, int index);
        void onAvatarClick(@NonNull Post post);
        void onAuthorClick(@NonNull Post post);
    }

    private final String currentUserId;
    private final Listener listener;

    @Nullable
    private String currentRole;

    public FeedAdapter(@NonNull String currentUserId, @NonNull Listener listener) {
        super(DIFF_CALLBACK);
        this.currentUserId = currentUserId;
        this.listener = listener;
    }

    public void setCurrentRole(@Nullable String role) {
        this.currentRole = role;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        h.bind(getItem(position));
    }

    static final DiffUtil.ItemCallback<Post> DIFF_CALLBACK = new DiffUtil.ItemCallback<Post>() {
        @Override
        public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }
        @Override
        public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.equals(newItem);
        }
    };

    class VH extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvAuthor, tvTime, tvContent, tvLikeCount, tvCommentCount;
        ImageButton btnEdit, btnDelete;
        LinearLayout layoutOwnerActions;
        GridLayout gridImages;
        ImageView[] imgSlots = new ImageView[4];
        MaterialButton btnLike, btnComment;

        VH(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            layoutOwnerActions = itemView.findViewById(R.id.layoutOwnerActions);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            gridImages = itemView.findViewById(R.id.gridImages);
            imgSlots[0] = itemView.findViewById(R.id.img1);
            imgSlots[1] = itemView.findViewById(R.id.img2);
            imgSlots[2] = itemView.findViewById(R.id.img3);
            imgSlots[3] = itemView.findViewById(R.id.img4);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnComment = itemView.findViewById(R.id.btnComment);
        }

        void bind(@NonNull Post p) {
            Context ctx = itemView.getContext();

            // Avatar + tên tác giả
            tvAuthor.setText(safeAuthorName(p));
            String avatarUrl = p.getAuthorAvatarUrl();

            if (!TextUtils.isEmpty(avatarUrl)) {
                Glide.with(ctx)
                        .load(Uri.parse(avatarUrl))
                        .placeholder(R.drawable.ic_avatar_placeholder)
                        .error(R.drawable.ic_avatar_placeholder)
                        .circleCrop()
                        .into(imgAvatar);
            } else {
                imgAvatar.setImageResource(R.drawable.ic_avatar_placeholder);
            }

            imgAvatar.setOnClickListener(v -> listener.onAvatarClick(p));
            tvAuthor.setOnClickListener(v -> listener.onAuthorClick(p));

            // Thời gian
            tvTime.setText("• " + formatTimeAgo(p.getCreatedAt(), System.currentTimeMillis(), ctx));

            // Nội dung (ẩn nếu trống)
            if (TextUtils.isEmpty(p.getContent())) {
                tvContent.setVisibility(View.GONE);
            } else {
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText(p.getContent());
            }

            // Ảnh (ẩn nếu không có)
            List<String> urls = p.getImageUrls();
            boolean hasImage = urls != null && !urls.isEmpty();
            for (int i = 0; i < imgSlots.length; i++) {
                ImageView iv = imgSlots[i];
                if (hasImage && i < urls.size() && !TextUtils.isEmpty(urls.get(i))) {
                    iv.setVisibility(View.VISIBLE);
                    final int index = i;
                    Glide.with(ctx)
                            .load(Uri.parse(urls.get(i))) // ✅ load cả content://
                            .placeholder(R.drawable.ic_avatar_placeholder)
                            .error(R.drawable.ic_avatar_placeholder)
                            .centerCrop()
                            .into(iv);
                    iv.setOnClickListener(v -> listener.onImageClick(p, index));
                } else {
                    iv.setVisibility(View.GONE);
                    iv.setOnClickListener(null);
                    iv.setImageDrawable(null);
                }
            }
            gridImages.setVisibility(hasImage ? View.VISIBLE : View.GONE);

            // Counters
            tvLikeCount.setText(String.format(Locale.getDefault(), "%d lượt thích", p.getLikeCount()));
            tvCommentCount.setText(String.format(Locale.getDefault(), "%d bình luận", p.getCommentCount()));

            // Action buttons
            btnLike.setOnClickListener(v -> listener.onLike(p));
            btnComment.setOnClickListener(v -> listener.onComment(p));

            // Quyền sửa/xóa
            boolean isOwner = !TextUtils.isEmpty(currentUserId)
                    && TextUtils.equals(currentUserId, p.getAuthorId());
            boolean isAdmin = "admin".equalsIgnoreCase(currentRole);

            boolean canModify = isOwner || isAdmin;
            layoutOwnerActions.setVisibility(canModify ? View.VISIBLE : View.GONE);

            btnEdit.setOnClickListener(v -> listener.onEdit(p));
            btnDelete.setOnClickListener(v -> listener.onDelete(p));
        }
    }

    // ===== Utils =====

    private String safeAuthorName(@NonNull Post p) {
        String name = p.getAuthorName();
        if (!TextUtils.isEmpty(name)) return name;

        try {
            String email = p.getAuthorEmail();
            if (!TextUtils.isEmpty(email)) {
                int at = email.indexOf('@');
                return at > 0 ? email.substring(0, at) : email;
            }
        } catch (Throwable ignored) {}

        if (!TextUtils.isEmpty(p.getAuthorId())
                && TextUtils.equals(p.getAuthorId(), currentUserId)
                && "admin".equalsIgnoreCase(currentRole)) {
            return "Admin";
        }
        return "Người dùng";
    }

    private String formatTimeAgo(long createdAtMillis, long nowMillis, Context ctx) {
        long diff = Math.max(0, nowMillis - createdAtMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        if (minutes < 1) return "vừa xong";
        if (minutes < 60) return minutes + " phút trước";
        long hours = TimeUnit.MINUTES.toHours(minutes);
        if (hours < 24) return hours + " giờ trước";
        long days = TimeUnit.HOURS.toDays(hours);
        if (days < 7) return days + " ngày trước";
        long weeks = days / 7;
        if (weeks < 4) return weeks + " tuần trước";
        long months = days / 30;
        if (months < 12) return months + " tháng trước";
        long years = days / 365;
        return years + " năm trước";
    }
}

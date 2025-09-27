package com.example.chubby.ui.comment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chubby.R;
import com.example.chubby.data.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.VH> {
    private List<Comment> list = new ArrayList<>();

    public void submitList(List<Comment> newList) {
        this.list = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Comment c = list.get(position);
        Context ctx = holder.itemView.getContext();

        // Author name
        holder.tvAuthor.setText(!TextUtils.isEmpty(c.getAuthorName()) ? c.getAuthorName() : "Ẩn danh");

        // Content
        holder.tvContent.setText(c.getContent());

        // Time
        if (c.getCreatedAt() != null) {
            holder.tvTime.setText(formatTimeAgo(c.getCreatedAt(), System.currentTimeMillis()));
        } else {
            holder.tvTime.setText("");
        }

        // Avatar
        if (!TextUtils.isEmpty(c.getAuthorAvatarUrl())) {
            Glide.with(ctx)
                    .load(c.getAuthorAvatarUrl())
                    .placeholder(R.drawable.ic_avatar_placeholder)
                    .error(R.drawable.ic_avatar_placeholder)
                    .circleCrop()
                    .into(holder.imgAvatar);
        } else {
            holder.imgAvatar.setImageResource(R.drawable.ic_avatar_placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvAuthor, tvContent, tvTime;

        VH(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvAuthor  = itemView.findViewById(R.id.tvAuthor);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime    = itemView.findViewById(R.id.tvTime);
        }
    }

    // ====== Utils: format "x phút trước" ======
    private String formatTimeAgo(long createdAtMillis, long nowMillis) {
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

package com.example.chubby.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Model cho một bài đăng trên feed. */
public class Post {

    private String id;                 // document id (Firestore)
    private String authorId;
    private String authorName;
    private String authorEmail;
    private String authorAvatarUrl;
    private String content;
    private long createdAt;
    private List<String> imageUrls;    // 0..4 ảnh
    private int likeCount;
    private int commentCount;


    public Post() { }

    /** Constructor cũ (giữ để không phá chỗ gọi cũ) */
    public Post(String id,
                String authorId,
                String authorName,
                String authorAvatarUrl,
                String content,
                long createdAt,
                List<String> imageUrls,
                int likeCount,
                int commentCount) {
        this(id, authorId, authorName, null, authorAvatarUrl,
                content, createdAt, imageUrls, likeCount, commentCount);
    }

    /** Constructor mới có thêm authorEmail */
    public Post(String id,
                String authorId,
                String authorName,
                String authorEmail,
                String authorAvatarUrl,
                String content,
                long createdAt,
                List<String> imageUrls,
                int likeCount,
                int commentCount) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.authorAvatarUrl = authorAvatarUrl;
        this.content = content;
        this.createdAt = createdAt;
        this.imageUrls = (imageUrls != null) ? new ArrayList<>(imageUrls) : new ArrayList<>();
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    // ====== Getters ======
    public String getId() { return id; }
    public String getAuthorId() { return authorId; }
    public String getAuthorName() { return authorName; }
    public String getAuthorEmail() { return authorEmail; }   // ✅ mới
    public String getAuthorAvatarUrl() { return authorAvatarUrl; }
    public String getContent() { return content; }
    public long getCreatedAt() { return createdAt; }
    public List<String> getImageUrls() { return imageUrls; }
    public int getLikeCount() { return likeCount; }
    public int getCommentCount() { return commentCount; }

    // ====== Setters ======
    public void setId(String id) { this.id = id; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; } //
    public void setAuthorAvatarUrl(String authorAvatarUrl) { this.authorAvatarUrl = authorAvatarUrl; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public void setCommentCount(int commentCount) { this.commentCount = commentCount; }

    // ====== equals/hashCode để DiffUtil làm việc chính xác ======
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return createdAt == post.createdAt
                && likeCount == post.likeCount
                && commentCount == post.commentCount
                && Objects.equals(id, post.id)
                && Objects.equals(authorId, post.authorId)
                && Objects.equals(authorName, post.authorName)
                && Objects.equals(authorEmail, post.authorEmail)          
                && Objects.equals(authorAvatarUrl, post.authorAvatarUrl)
                && Objects.equals(content, post.content)
                && Objects.equals(imageUrls, post.imageUrls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorId, authorName, authorEmail,
                authorAvatarUrl, content, createdAt, imageUrls,
                likeCount, commentCount);
    }

    @NonNull
    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", authorId='" + authorId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", createdAt=" + createdAt +
                ", likeCount=" + likeCount +
                ", commentCount=" + commentCount +
                '}';
    }
}

package com.example.chubby.data;

import com.google.firebase.Timestamp;

public class Comment {
    private String id;
    private String authorId;
    private String authorName;
    private String authorAvatarUrl; //
    private String content;
    private Long createdAt; // để nullable, tránh crash khi Firestore mapping

    public Comment() { }

    // ===== Getter & Setter =====
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorAvatarUrl() { return authorAvatarUrl; }
    public void setAuthorAvatarUrl(String authorAvatarUrl) { this.authorAvatarUrl = authorAvatarUrl; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    // 🔹 Hỗ trợ map từ Firestore Timestamp (nếu Firestore trả về Timestamp thay vì Long)
    public void setCreatedAt(Timestamp ts) {
        if (ts != null) {
            this.createdAt = ts.toDate().getTime();
        }
    }
}

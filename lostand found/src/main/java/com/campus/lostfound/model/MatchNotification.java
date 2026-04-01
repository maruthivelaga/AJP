package com.campus.lostfound.model;

import java.time.LocalDateTime;

public class MatchNotification {
    private int id;
    private int lostItemId;
    private int foundItemId;
    private String lostStudentEmail;
    private String message;
    private String meetingSuggestion;
    private LocalDateTime createdAt;

    public MatchNotification(int id, int lostItemId, int foundItemId, String lostStudentEmail,
                             String message, String meetingSuggestion, LocalDateTime createdAt) {
        this.id = id;
        this.lostItemId = lostItemId;
        this.foundItemId = foundItemId;
        this.lostStudentEmail = lostStudentEmail;
        this.message = message;
        this.meetingSuggestion = meetingSuggestion;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getLostItemId() {
        return lostItemId;
    }

    public int getFoundItemId() {
        return foundItemId;
    }

    public String getLostStudentEmail() {
        return lostStudentEmail;
    }

    public String getMessage() {
        return message;
    }

    public String getMeetingSuggestion() {
        return meetingSuggestion;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

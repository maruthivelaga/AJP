package com.campus.lostfound.model;

import java.time.LocalDateTime;

public class DisputeCase {
    private int id;
    private int itemId;
    private String reason;
    private String status;
    private String resolution;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    public DisputeCase(int id, int itemId, String reason, String status, String resolution,
                       LocalDateTime createdAt, LocalDateTime resolvedAt) {
        this.id = id;
        this.itemId = itemId;
        this.reason = reason;
        this.status = status;
        this.resolution = resolution;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public String getResolution() {
        return resolution;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
}

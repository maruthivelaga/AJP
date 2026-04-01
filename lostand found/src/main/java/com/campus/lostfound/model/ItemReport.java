package com.campus.lostfound.model;

import java.time.LocalDateTime;

public class ItemReport {
    private int id;
    private ItemType type;
    private String studentName;
    private String email;
    private String description;
    private String location;
    private LocalDateTime reportedAt;
    private boolean verified;
    private ItemStatus status;

    public ItemReport() {
    }

    public ItemReport(int id, ItemType type, String studentName, String email, String description,
                      String location, LocalDateTime reportedAt, boolean verified, ItemStatus status) {
        this.id = id;
        this.type = type;
        this.studentName = studentName;
        this.email = email;
        this.description = description;
        this.location = location;
        this.reportedAt = reportedAt;
        this.verified = verified;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public boolean isVerified() {
        return verified;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
}

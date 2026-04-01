package com.campus.lostfound.action;

import com.campus.lostfound.model.ItemType;
import com.campus.lostfound.service.LostFoundService;
import com.opensymphony.xwork2.ActionSupport;

public class ReportItemAction extends ActionSupport {
    private final LostFoundService service = LostFoundService.getInstance();

    private String type;
    private String studentName;
    private String email;
    private String description;
    private String location;

    @Override
    public String execute() {
        return SUCCESS;
    }

    public String submit() {
        if (isBlank(type) || isBlank(studentName) || isBlank(email)
                || isBlank(description) || isBlank(location)) {
            addActionError("All fields are required.");
            return INPUT;
        }

        try {
            ItemType itemType = ItemType.valueOf(type.trim().toUpperCase());
            service.reportItem(itemType, studentName.trim(), email.trim(), description.trim(), location.trim());
            addActionMessage("Item report submitted successfully.");
            return SUCCESS;
        } catch (IllegalArgumentException ex) {
            addActionError("Invalid item type. Use LOST or FOUND.");
            return INPUT;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

package com.campus.lostfound.action;

import com.campus.lostfound.model.MatchNotification;
import com.campus.lostfound.service.LostFoundService;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

public class NotificationAction extends ActionSupport {
    private final LostFoundService service = LostFoundService.getInstance();

    private List<MatchNotification> notifications;

    @Override
    public String execute() {
        notifications = service.getNotifications();
        return SUCCESS;
    }

    public String runMatching() {
        int count = service.runMatching();
        addActionMessage("Matching completed. New notifications generated: " + count);
        notifications = service.getNotifications();
        return SUCCESS;
    }

    public List<MatchNotification> getNotifications() {
        return notifications;
    }
}

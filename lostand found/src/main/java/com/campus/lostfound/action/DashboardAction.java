package com.campus.lostfound.action;

import com.campus.lostfound.model.ItemReport;
import com.campus.lostfound.model.MatchNotification;
import com.campus.lostfound.service.LostFoundService;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

public class DashboardAction extends ActionSupport {
    private final LostFoundService service = LostFoundService.getInstance();

    private List<ItemReport> reports;
    private List<MatchNotification> notifications;

    @Override
    public String execute() {
        reports = service.getAllReports();
        notifications = service.getNotifications();
        return SUCCESS;
    }

    public List<ItemReport> getReports() {
        return reports;
    }

    public List<MatchNotification> getNotifications() {
        return notifications;
    }
}

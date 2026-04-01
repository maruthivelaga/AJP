package com.campus.lostfound.action;

import com.campus.lostfound.model.DisputeCase;
import com.campus.lostfound.model.ItemReport;
import com.campus.lostfound.service.LostFoundService;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

public class AdminAction extends ActionSupport {
    private final LostFoundService service = LostFoundService.getInstance();

    private int itemId;
    private int disputeId;
    private String reason;
    private String resolution;

    private List<ItemReport> pendingFoundItems;
    private List<ItemReport> allReports;
    private List<DisputeCase> disputes;

    @Override
    public String execute() {
        loadData();
        return SUCCESS;
    }

    public String verifyFound() {
        boolean updated = service.verifyFoundItem(itemId);
        if (updated) {
            addActionMessage("Found item verified successfully.");
        } else {
            addActionError("Unable to verify item. Please check item ID.");
        }
        loadData();
        return SUCCESS;
    }

    public String openDispute() {
        if (reason == null || reason.trim().isEmpty()) {
            addActionError("Reason is required to open dispute.");
        } else {
            boolean created = service.openDispute(itemId, reason.trim());
            if (created) {
                addActionMessage("Dispute opened successfully.");
            } else {
                addActionError("Unable to open dispute. Item ID not found.");
            }
        }
        loadData();
        return SUCCESS;
    }

    public String resolveDispute() {
        if (resolution == null || resolution.trim().isEmpty()) {
            addActionError("Resolution note is required.");
        } else {
            boolean resolved = service.resolveDispute(disputeId, resolution.trim());
            if (resolved) {
                addActionMessage("Dispute resolved and item marked as retrieved.");
            } else {
                addActionError("Unable to resolve dispute. Check dispute ID.");
            }
        }
        loadData();
        return SUCCESS;
    }

    private void loadData() {
        pendingFoundItems = service.getPendingFoundVerification();
        allReports = service.getAllReports();
        disputes = service.getDisputes();
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setDisputeId(int disputeId) {
        this.disputeId = disputeId;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public List<ItemReport> getPendingFoundItems() {
        return pendingFoundItems;
    }

    public List<ItemReport> getAllReports() {
        return allReports;
    }

    public List<DisputeCase> getDisputes() {
        return disputes;
    }
}

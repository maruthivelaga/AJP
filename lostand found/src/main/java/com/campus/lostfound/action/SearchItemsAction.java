package com.campus.lostfound.action;

import com.campus.lostfound.model.ItemReport;
import com.campus.lostfound.service.LostFoundService;
import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchItemsAction extends ActionSupport {
    private final LostFoundService service = LostFoundService.getInstance();

    private String query;
    private List<ItemReport> results = new ArrayList<>();

    @Override
    public String execute() {
        results = service.searchByDescription(query);
        return SUCCESS;
    }

    public String getQuery() {
        return query;
    }

    public List<ItemReport> getResults() {
        return results;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

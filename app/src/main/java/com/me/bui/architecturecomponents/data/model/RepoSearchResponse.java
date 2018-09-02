package com.me.bui.architecturecomponents.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class RepoSearchResponse {
    @SerializedName("total_count")
    private int total;
    @SerializedName("items")
    private List<Repo> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Repo> getItems() {
        return items;
    }

    public void setItems(List<Repo> items) {
        this.items = items;
    }
}

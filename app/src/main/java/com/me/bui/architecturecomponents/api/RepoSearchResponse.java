package com.me.bui.architecturecomponents.api;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.me.bui.architecturecomponents.data.model.Repo;

import java.util.ArrayList;
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

    @NonNull
    public List<Integer> getRepoIds() {
        List<Integer> repoIds = new ArrayList<>();
        for (Repo repo : items) {
            repoIds.add(repo.id);
        }
        return repoIds;
    }
}

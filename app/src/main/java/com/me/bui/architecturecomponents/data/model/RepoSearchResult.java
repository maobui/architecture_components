package com.me.bui.architecturecomponents.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.me.bui.architecturecomponents.data.db.GithubTypeConverters;

import java.util.List;

/**
 * Created by mao.bui on 9/3/2018.
 */

@Entity
@TypeConverters(GithubTypeConverters.class)
public class RepoSearchResult {
    @NonNull
    @PrimaryKey
    public final String query;
    public final List<Integer> repoIds;
    public final int totalCount;

    public RepoSearchResult(@NonNull String query, List<Integer> repoIds, int totalCount) {
        this.query = query;
        this.repoIds = repoIds;
        this.totalCount = totalCount;
    }
}

package com.me.bui.architecturecomponents.data.db;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.util.SparseIntArray;

import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.RepoSearchResult;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mao.bui on 9/3/2018.
 */

@Dao
public abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Repo... repos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRepos(List<Repo> repositories);

    @Query("SELECT * FROM repo WHERE owner_login = :login AND name = :name")
    public abstract LiveData<Repo> load(String login, String name);
    @Query("SELECT * FROM Repo "
            + "WHERE owner_login = :owner "
            + "ORDER BY stars DESC")
    public abstract LiveData<List<Repo>> loadRepositories(String owner);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(RepoSearchResult result);

    @Query("SELECT * FROM RepoSearchResult WHERE query = :query")
    public abstract LiveData<RepoSearchResult> search(String query);

    public LiveData<List<Repo>> loadOrdered(List<Integer> repoIds) {
        final SparseIntArray order = new SparseIntArray();
        int index = 0;
        for (Integer repoId : repoIds) {
            order.put(repoId, index++);
        }
        return Transformations.map(loadById(repoIds), new Function<List<Repo>, List<Repo>>() {
            @Override
            public List<Repo> apply(List<Repo> repos) {
                Collections.sort(repos, new Comparator<Repo>() {
                    @Override
                    public int compare(Repo r1, Repo r2) {
                        int pos1 = order.get(r1.id);
                        int pos2 = order.get(r2.id);
                        return pos1 - pos2;
                    }
                });
                return repos;
            }
        });
    }

    @Query("SELECT * FROM Repo WHERE id in (:repoIds)")
    protected abstract LiveData<List<Repo>> loadById(List<Integer> repoIds);

    @Query("SELECT * FROM RepoSearchResult WHERE query = :query")
    public abstract RepoSearchResult findSearchResult(String query);
}
package com.me.bui.architecturecomponents.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.RepoSearchResult;

import java.util.List;

/**
 * Created by mao.bui on 9/3/2018.
 */

@Dao
public abstract class RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRepos(List<Repo> repositories);

    @Query("SELECT * FROM Repo WHERE id in (:repoIds)")
    protected abstract List<Repo> loadById(List<Integer> repoIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(RepoSearchResult result);

    @Query("SELECT * FROM RepoSearchResult WHERE query = :query")
    public abstract RepoSearchResult search(String query);
}

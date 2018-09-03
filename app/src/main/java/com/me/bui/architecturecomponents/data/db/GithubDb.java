package com.me.bui.architecturecomponents.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.RepoSearchResult;

/**
 * Created by mao.bui on 9/3/2018.
 */
@Database(entities = {RepoSearchResult.class, Repo.class}, version = 1)
public abstract class GithubDb extends RoomDatabase {
    abstract public RepoDao repoDao();
}

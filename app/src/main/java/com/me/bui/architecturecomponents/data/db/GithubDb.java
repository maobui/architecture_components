package com.me.bui.architecturecomponents.data.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.RepoSearchResult;

/**
 * Created by mao.bui on 9/3/2018.
 */
@Database(entities = {RepoSearchResult.class, Repo.class}, version = 2)
public abstract class GithubDb extends RoomDatabase {
    abstract public RepoDao repoDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Repo "
                    + " ADD COLUMN html_url TEXT");
        }
    };
}

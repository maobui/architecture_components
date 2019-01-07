package com.me.bui.architecturecomponents.data.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration

import com.me.bui.architecturecomponents.data.model.Repo
import com.me.bui.architecturecomponents.data.model.RepoSearchResult

/**
 * Created by mao.bui on 9/3/2018.
 */
@Database(entities = [RepoSearchResult::class, Repo::class], version = 2)
abstract class GithubDb : RoomDatabase() {
    abstract fun repoDao(): RepoDao

    companion object {

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Repo " + " ADD COLUMN html_url TEXT")
            }
        }
    }
}

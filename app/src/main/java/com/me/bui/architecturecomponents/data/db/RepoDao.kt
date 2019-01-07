package com.me.bui.architecturecomponents.data.db

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.me.bui.architecturecomponents.data.model.Repo
import com.me.bui.architecturecomponents.data.model.RepoSearchResult

import io.reactivex.Flowable
import io.reactivex.Maybe


/**
 * Created by mao.bui on 9/3/2018.
 */

@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repos: Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repo>?)

    @Query("SELECT * FROM repo WHERE owner_login = :login AND name = :name")
    abstract fun load(login: String, name: String): LiveData<Repo>

    @Query("SELECT * FROM Repo "
            + "WHERE owner_login = :owner "
            + "ORDER BY stars DESC")
    abstract fun loadRepositories(owner: String): LiveData<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: RepoSearchResult)

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun search(query: String): LiveData<RepoSearchResult>

    @Query("SELECT * FROM Repo WHERE id in (:repoIds)")
    abstract fun loadById(repoIds: List<Int>): DataSource.Factory<Int, Repo>

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun findSearchResult(query: String): RepoSearchResult

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun rxSearch(query: String): Flowable<RepoSearchResult>

    @Query("SELECT * FROM Repo WHERE id in (:repoIds)")
    abstract fun rxLoadById(repoIds: List<Int>): Flowable<List<Repo>>

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun rxSearchSync(query: String): Maybe<RepoSearchResult>
}

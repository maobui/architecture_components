package com.me.bui.architecturecomponents.data

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList

import com.me.bui.architecturecomponents.api.ApiResponse
import com.me.bui.architecturecomponents.api.GithubService
import com.me.bui.architecturecomponents.api.RepoSearchResponse
import com.me.bui.architecturecomponents.data.db.RepoDao
import com.me.bui.architecturecomponents.data.model.Repo
import com.me.bui.architecturecomponents.data.model.RepoSearchResult
import com.me.bui.architecturecomponents.data.model.Resource
import com.me.bui.architecturecomponents.data.model.User
import com.me.bui.architecturecomponents.util.AbsentLiveData

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

/**
 * Created by mao.bui on 9/3/2018.
 */
@Singleton
class RepoRepository @Inject
constructor(private val repoDao: RepoDao, private val githubService: GithubService) {

    fun search(query: String): LiveData<Resource<PagedList<Repo>>> {
        return object : NetworkBoundResource<PagedList<Repo>, RepoSearchResponse>() {
            override fun loadFromDb(): LiveData<PagedList<Repo>> {
                return Transformations.switchMap(repoDao.search(query)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        LivePagedListBuilder(repoDao.loadById(searchData.repoIds!!), 1).build()
                    }
                }
            }

            override fun shouldFetch(data: PagedList<Repo>?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<RepoSearchResponse>> {
                return githubService.searchRepos(query)
            }

            override fun saveCallResult(item: RepoSearchResponse) {
                val repoIds = item.repoIds
                val repoSearchResult = RepoSearchResult(query, repoIds, item.total)

                repoDao.insertRepos(item.items)
                repoDao.insert(repoSearchResult)
            }
        }.asLiveData()
    }

    fun searchRepoRX(query: String): Observable<Response<RepoSearchResponse>> {
        return githubService.searchReposRX(query)
    }

    fun getUser(login: String): Observable<Response<User>> {
        return githubService.getUser(login)
    }

    fun rxSearch(query: String): Flowable<RepoSearchResult> {
        return repoDao.rxSearch(query)
    }

    fun rxLoadById(repoIds: List<Int>): Flowable<List<Repo>> {
        return repoDao.rxLoadById(repoIds)
    }

    fun rxSearchSync(query: String): Maybe<RepoSearchResult> {
        return repoDao.rxSearchSync(query)
    }
}

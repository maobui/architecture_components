package com.me.bui.architecturecomponents.viewmodel

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList

import com.me.bui.architecturecomponents.api.RepoSearchResponse
import com.me.bui.architecturecomponents.data.RepoRepository
import com.me.bui.architecturecomponents.data.model.Repo
import com.me.bui.architecturecomponents.data.model.RepoSearchResult
import com.me.bui.architecturecomponents.data.model.Resource
import com.me.bui.architecturecomponents.data.model.User
import com.me.bui.architecturecomponents.util.AbsentLiveData

import javax.inject.Inject

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

/**
 * Created by mao.bui on 9/1/2018.
 */
class RepoViewModel @Inject
constructor(private val mRepoRepository: RepoRepository) : ViewModel() {


    private val query = MutableLiveData<String>()

    val repos: LiveData<Resource<PagedList<Repo>>>

    init {
        repos = Transformations.switchMap(query) { input ->
            if (input == null || input.isEmpty()) {
                AbsentLiveData.create()
            } else {
                mRepoRepository.search(input)
            }
        }
    }

    fun searchRepo(input: String) {
        query.value = input
    }

    fun searchRepoRX(query: String): Observable<Response<RepoSearchResponse>> {
        return mRepoRepository.searchRepoRX(query)
    }

    fun getUser(login: String): Observable<Response<User>> {
        return mRepoRepository.getUser(login)
    }

    fun rxSearch(query: String): Flowable<RepoSearchResult> {
        return mRepoRepository.rxSearch(query)
    }

    fun rxLoadById(repoIds: List<Int>): Flowable<List<Repo>> {
        return mRepoRepository.rxLoadById(repoIds)
    }

    fun rxSearchSync(query: String): Maybe<RepoSearchResult> {
        return mRepoRepository.rxSearchSync(query)
    }
}

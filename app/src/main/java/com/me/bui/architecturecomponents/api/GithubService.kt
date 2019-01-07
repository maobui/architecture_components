package com.me.bui.architecturecomponents.api

import android.arch.lifecycle.LiveData

import com.me.bui.architecturecomponents.data.model.User

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by mao.bui on 9/1/2018.
 */
interface GithubService {
    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String): LiveData<ApiResponse<RepoSearchResponse>>

    @GET("search/repositories")
    fun searchReposRX(@Query("q") query: String): Observable<Response<RepoSearchResponse>>

    @GET("users/{login}")
    fun getUser(@Path("login") login: String): Observable<Response<User>>
}

package com.me.bui.architecturecomponents.api;

import android.arch.lifecycle.LiveData;

import com.me.bui.architecturecomponents.data.model.User;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mao.bui on 9/1/2018.
 */
public interface GithubService {
    @GET("search/repositories")
    LiveData<ApiResponse<RepoSearchResponse>> searchRepos(@Query("q") String query);

    @GET("search/repositories")
    Observable<Response<RepoSearchResponse>> searchReposRX(@Query("q") String query);

    @GET("users/{login}")
    Observable<Response<User>> getUser(@Path("login") String login);
}

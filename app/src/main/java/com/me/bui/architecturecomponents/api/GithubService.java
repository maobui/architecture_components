package com.me.bui.architecturecomponents.api;

import android.arch.lifecycle.LiveData;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mao.bui on 9/1/2018.
 */
public interface GithubService {
    @GET("search/repositories")
    LiveData<ApiResponse<RepoSearchResponse>> searchRepos(@Query("q") String query);
}

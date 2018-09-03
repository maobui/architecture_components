package com.me.bui.architecturecomponents.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.me.bui.architecturecomponents.api.ApiResponse;
import com.me.bui.architecturecomponents.api.GithubService;
import com.me.bui.architecturecomponents.api.RetrofitManager;
import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.RepoSearchResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mao.bui on 9/1/2018.
 */

@Singleton
public class DataModel {

    private GithubService githubService;

    @Inject
    public DataModel(GithubService githubService) {
        this.githubService = githubService;
    }

    public LiveData<ApiResponse<RepoSearchResponse>> searchRepo(String query) {
       return githubService.searchRepos(query);
    }
}

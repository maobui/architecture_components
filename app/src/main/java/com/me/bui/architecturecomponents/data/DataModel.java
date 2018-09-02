package com.me.bui.architecturecomponents.data;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.me.bui.architecturecomponents.api.GithubService;
import com.me.bui.architecturecomponents.api.RetrofitManager;
import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.RepoSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class DataModel {

    private GithubService githubService = RetrofitManager.getAPI();

    public void searchRepo(String query, final onDataReadyCallback callback) {
        githubService.searchRepos(query)
                .enqueue(new Callback<RepoSearchResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<RepoSearchResponse> call, @NonNull Response<RepoSearchResponse> response) {
                        callback.onDataReady(response.body().getItems());
                    }

                    @Override
                    public void onFailure(@NonNull Call<RepoSearchResponse> call, @NonNull Throwable t) {
                        // TODO: error handle
                    }
                });
    }

    public interface onDataReadyCallback {
        void onDataReady(List<Repo> data);
    }
}

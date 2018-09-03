package com.me.bui.architecturecomponents.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class RetrofitManager {

    private static RetrofitManager mInstance = new RetrofitManager();

    private GithubService githubService;

    private RetrofitManager() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();

        githubService = retrofit.create(GithubService.class);
    }

    public static GithubService getAPI() {
        return mInstance.githubService;
    }
}

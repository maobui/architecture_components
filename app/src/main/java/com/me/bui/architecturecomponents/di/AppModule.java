package com.me.bui.architecturecomponents.di;

import com.google.gson.Gson;
import com.me.bui.architecturecomponents.api.GithubService;
import com.me.bui.architecturecomponents.api.LiveDataCallAdapterFactory;
import com.me.bui.architecturecomponents.api.RetrofitManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mao.bui on 9/3/2018.
 */

@Module
class AppModule {

    @Provides
    @Singleton
    GithubService provideGithubService() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(GithubService.class);
    }
}

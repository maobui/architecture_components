package com.me.bui.architecturecomponents.di;

import android.arch.persistence.room.Room;

import com.me.bui.architecturecomponents.GithubApp;
import com.me.bui.architecturecomponents.api.GithubService;
import com.me.bui.architecturecomponents.api.LiveDataCallAdapterFactory;
import com.me.bui.architecturecomponents.data.db.GithubDb;
import com.me.bui.architecturecomponents.data.db.RepoDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.me.bui.architecturecomponents.data.db.GithubDb.MIGRATION_1_2;

/**
 * Created by mao.bui on 9/3/2018.
 */

@Module(includes = ViewModelModule.class)
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

    @Provides
    @Singleton
    GithubDb provideDb(GithubApp app) {
        return Room.databaseBuilder(app, GithubDb.class,"github.db")
                .addMigrations(MIGRATION_1_2)
                .build();
    }
    @Provides
    @Singleton
    RepoDao provideRepoDao(GithubDb db) {
        return db.repoDao();
    }
}

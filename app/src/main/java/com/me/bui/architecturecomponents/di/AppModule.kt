package com.me.bui.architecturecomponents.di

import android.arch.persistence.room.Room

import com.me.bui.architecturecomponents.GithubApp
import com.me.bui.architecturecomponents.api.GithubService
import com.me.bui.architecturecomponents.api.LiveDataCallAdapterFactory
import com.me.bui.architecturecomponents.data.db.GithubDb
import com.me.bui.architecturecomponents.data.db.RepoDao

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import com.me.bui.architecturecomponents.data.db.GithubDb.Companion.MIGRATION_1_2

/**
 * Created by mao.bui on 9/3/2018.
 */

@Module(includes = arrayOf(ViewModelModule::class))
class AppModule {

    @Provides
    @Singleton
    fun provideGithubService(): GithubService {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GithubService::class.java)
    }

    @Provides
    @Singleton
    fun provideDb(app: GithubApp): GithubDb {
        return Room.databaseBuilder(app, GithubDb::class.java, "github.db")
                .addMigrations(MIGRATION_1_2)
                .build()
    }

    @Provides
    @Singleton
    fun provideRepoDao(db: GithubDb): RepoDao {
        return db.repoDao()
    }
}

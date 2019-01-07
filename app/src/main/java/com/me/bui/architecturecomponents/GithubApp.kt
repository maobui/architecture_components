package com.me.bui.architecturecomponents

import android.app.Activity
import android.app.Application

import com.me.bui.architecturecomponents.di.AppInjector
import com.me.bui.architecturecomponents.di.DaggerAppComponent

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

/**
 * Created by mao.bui on 9/3/2018.
 */
class GithubApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        // Dagger2
        AppInjector.init(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }
}

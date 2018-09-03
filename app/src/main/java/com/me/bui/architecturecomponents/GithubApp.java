package com.me.bui.architecturecomponents;

import android.app.Activity;
import android.app.Application;

import com.me.bui.architecturecomponents.di.AppInjector;
import com.me.bui.architecturecomponents.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by mao.bui on 9/3/2018.
 */
public class GithubApp extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger2
        AppInjector.init(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}

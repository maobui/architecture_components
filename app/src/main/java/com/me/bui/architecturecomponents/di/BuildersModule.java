package com.me.bui.architecturecomponents.di;

import com.me.bui.architecturecomponents.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mao.bui on 9/3/2018.
 */

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();
}

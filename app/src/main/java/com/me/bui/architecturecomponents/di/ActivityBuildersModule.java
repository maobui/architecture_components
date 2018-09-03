package com.me.bui.architecturecomponents.di;

import com.me.bui.architecturecomponents.MainActivity;
import com.me.bui.architecturecomponents.ui.RepoFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mao.bui on 9/3/2018.
 */

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();
}

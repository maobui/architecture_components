package com.me.bui.architecturecomponents.di

import com.me.bui.architecturecomponents.ui.RepoFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by mao.bui on 9/3/2018.
 */

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): RepoFragment
}

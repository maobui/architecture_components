package com.me.bui.architecturecomponents.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import com.me.bui.architecturecomponents.viewmodel.GithubViewModelFactory
import com.me.bui.architecturecomponents.viewmodel.RepoViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by mao.bui on 9/3/2018.
 */

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    abstract fun bindRepoViewModel(repoViewModel: RepoViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}
package com.me.bui.architecturecomponents.di

import com.me.bui.architecturecomponents.GithubApp

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by mao.bui on 9/3/2018.
 */

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, AppModule::class, ActivityBuildersModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: GithubApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: GithubApp)
}

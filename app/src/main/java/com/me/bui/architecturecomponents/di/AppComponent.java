package com.me.bui.architecturecomponents.di;

import com.me.bui.architecturecomponents.GithubApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by mao.bui on 9/3/2018.
 */

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuildersModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(GithubApp application);
        AppComponent build();
    }

    void inject(GithubApp app);
}

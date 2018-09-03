package com.me.bui.architecturecomponents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.me.bui.architecturecomponents.api.GithubService;
import com.me.bui.architecturecomponents.di.Injectable;
import com.me.bui.architecturecomponents.ui.RepoFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements Injectable, HasSupportFragmentInjector{

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String tag = RepoFragment.TAG;
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            RepoFragment fragment = RepoFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, tag)
                    .commit();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}

package com.me.bui.architecturecomponents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.me.bui.architecturecomponents.api.GithubService;
import com.me.bui.architecturecomponents.ui.RepoFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Inject
    GithubService mGithubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(mGithubService != null){
            Log.e("Dagger2", "Hello Dagger.");
        }

        String tag = RepoFragment.TAG;
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            RepoFragment fragment = RepoFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, tag)
                    .commit();
        }
    }
}

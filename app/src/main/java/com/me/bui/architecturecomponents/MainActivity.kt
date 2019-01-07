package com.me.bui.architecturecomponents

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.me.bui.architecturecomponents.api.GithubService
import com.me.bui.architecturecomponents.di.Injectable
import com.me.bui.architecturecomponents.ui.RepoFragment

import javax.inject.Inject

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector

class MainActivity : AppCompatActivity(), Injectable, HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tag = RepoFragment.TAG
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            val fragment = RepoFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment, tag)
                    .commit()
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentDispatchingAndroidInjector
    }
}

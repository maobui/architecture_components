package com.me.bui.architecturecomponents.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.me.bui.architecturecomponents.data.DataModel;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mao.bui on 9/1/2018.
 */

@Singleton
public class GithubViewModelFactory implements ViewModelProvider.Factory{

    private DataModel dataModel;

    @Inject
    public GithubViewModelFactory(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RepoViewModel.class)) {
            return (T) new RepoViewModel(dataModel);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

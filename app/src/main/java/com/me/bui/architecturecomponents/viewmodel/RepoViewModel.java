package com.me.bui.architecturecomponents.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.me.bui.architecturecomponents.data.RepoRepository;
import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.Resource;
import com.me.bui.architecturecomponents.util.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class RepoViewModel extends ViewModel {


    private final MutableLiveData<String> query = new MutableLiveData<>();

    private final LiveData<Resource<List<Repo>>> repos;

    private RepoRepository mRepoRepository;

    @Inject
    public RepoViewModel(RepoRepository repoRepository) {
        super();
        mRepoRepository = repoRepository;
        repos = Transformations.switchMap(query, new Function<String, LiveData<Resource<List<Repo>>>>() {
            @Override
            public LiveData<Resource<List<Repo>>> apply(String input) {
                if(TextUtils.isEmpty(input)) {
                    return AbsentLiveData.create();
                } else {
                    return mRepoRepository.search(input);
                }
            }
        });
    }

    public LiveData<Resource<List<Repo>>> getRepos() {
        return repos;
    }

    public void searchRepo(String input) {
        query.setValue(input);
    }
}

package com.me.bui.architecturecomponents.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.me.bui.architecturecomponents.data.RepoRepository;
import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.Resource;
import com.me.bui.architecturecomponents.util.AbsentLiveData;

import javax.inject.Inject;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class RepoViewModel extends ViewModel {


    private final MutableLiveData<String> query = new MutableLiveData<>();

    private final LiveData<Resource<PagedList<Repo>>> repos;

    private RepoRepository mRepoRepository;

    @Inject
    public RepoViewModel(RepoRepository repoRepository) {
        super();
        mRepoRepository = repoRepository;
        repos = Transformations.switchMap(query, new Function<String, LiveData<Resource<PagedList<Repo>>>>() {
            @Override
            public LiveData<Resource<PagedList<Repo>>> apply(String input) {
                if(input == null || input.isEmpty()) {
                    return AbsentLiveData.create();
                } else {
                    return mRepoRepository.search(input);
                }
            }
        });
    }

    public LiveData<Resource<PagedList<Repo>>> getRepos() {
        return repos;
    }

    public void searchRepo(String input) {
        query.setValue(input);
    }
}

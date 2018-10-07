package com.me.bui.architecturecomponents.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.me.bui.architecturecomponents.api.RepoSearchResponse;
import com.me.bui.architecturecomponents.data.RepoRepository;
import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.RepoSearchResult;
import com.me.bui.architecturecomponents.data.model.Resource;
import com.me.bui.architecturecomponents.data.model.User;
import com.me.bui.architecturecomponents.util.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class RepoViewModel extends ViewModel {


    private final MutableLiveData<String> query = new MutableLiveData<>();

    public final LiveData<Resource<PagedList<Repo>>> repos;

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

    public Observable<Response<RepoSearchResponse>> searchRepoRX(String query) {
        return mRepoRepository.searchRepoRX(query);
    }

    public Observable<Response<User>> getUser(String login) {
        return mRepoRepository.getUser(login);
    }

    public Flowable<RepoSearchResult> rxSearch(String query) {
        return mRepoRepository.rxSearch(query);
    }
    public Flowable<List<Repo>> rxLoadById(List<Integer> repoIds) {
        return mRepoRepository.rxLoadById(repoIds);
    }
    public Maybe<RepoSearchResult> rxSearchSync(String query) {
        return mRepoRepository.rxSearchSync(query);
    }
}

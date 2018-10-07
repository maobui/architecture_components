package com.me.bui.architecturecomponents.data;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.me.bui.architecturecomponents.api.ApiResponse;
import com.me.bui.architecturecomponents.api.GithubService;
import com.me.bui.architecturecomponents.api.RepoSearchResponse;
import com.me.bui.architecturecomponents.data.db.RepoDao;
import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.RepoSearchResult;
import com.me.bui.architecturecomponents.data.model.Resource;
import com.me.bui.architecturecomponents.data.model.User;
import com.me.bui.architecturecomponents.util.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;

/**
 * Created by mao.bui on 9/3/2018.
 */
@Singleton
public class RepoRepository {

    private RepoDao repoDao;

    private GithubService githubService;

    @Inject
    public RepoRepository(RepoDao repoDao, GithubService githubService) {
        this.repoDao = repoDao;
        this.githubService = githubService;
    }

    public LiveData<Resource<PagedList<Repo>>> search(final String query) {
        return new NetworkBoundResource<PagedList<Repo>, RepoSearchResponse>() {
            @NonNull
            @Override
            protected LiveData<PagedList<Repo>> loadFromDb() {
                return Transformations.switchMap(repoDao.search(query), new Function<RepoSearchResult, LiveData<PagedList<Repo>>>() {
                    @Override
                    public LiveData<PagedList<Repo>> apply(RepoSearchResult searchData) {
                        if (searchData == null) {
                            return AbsentLiveData.create();
                        } else {
                            return new LivePagedListBuilder<>(repoDao.loadById(searchData.repoIds), 1).build();
                        }
                    }
                });
            }

            @Override
            protected boolean shouldFetch(@Nullable PagedList<Repo> data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RepoSearchResponse>> createCall() {
                return githubService.searchRepos(query);
            }

            @Override
            protected void saveCallResult(@NonNull RepoSearchResponse item) {
                List<Integer> repoIds = item.getRepoIds();
                RepoSearchResult repoSearchResult =
                        new RepoSearchResult(query, repoIds, item.getTotal());

                repoDao.insertRepos(item.getItems());
                repoDao.insert(repoSearchResult);
            }
        }.asLiveData();
    }

    public Observable<Response<RepoSearchResponse>> searchRepoRX(String query) {
        return githubService.searchReposRX(query);
    }

    public Observable<Response<User>> getUser(String login) {
        return githubService.getUser(login);
    }

    public Flowable<RepoSearchResult> rxSearch(String query) {
        return repoDao.rxSearch(query);
    }

    public Flowable<List<Repo>> rxLoadById(List<Integer> repoIds) {
        return repoDao.rxLoadById(repoIds);
    }

    public Maybe<RepoSearchResult> rxSearchSync(String query) {
        return repoDao.rxSearchSync(query);
    }
}

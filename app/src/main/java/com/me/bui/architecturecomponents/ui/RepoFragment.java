package com.me.bui.architecturecomponents.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.me.bui.architecturecomponents.api.RepoSearchResponse;
import com.me.bui.architecturecomponents.api.RepoSearchResponseAndUser;
import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.RepoSearchResult;
import com.me.bui.architecturecomponents.data.model.Resource;
import com.me.bui.architecturecomponents.data.model.User;
import com.me.bui.architecturecomponents.databinding.FragmentRepoBinding;
import com.me.bui.architecturecomponents.di.Injectable;
import com.me.bui.architecturecomponents.viewmodel.RepoViewModel;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class RepoFragment extends Fragment implements Injectable{

    public static final String TAG = RepoFragment.class.getSimpleName();


    private FragmentRepoBinding binding;

    @Inject
    ViewModelProvider.Factory factory;
    private RepoViewModel viewModel;

    private RepoPageAdapter mRepoPageAdapter = new RepoPageAdapter();
    private RepoAdapter mRepoAdapter = new RepoAdapter();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public static RepoFragment newInstance() {
        return new RepoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRepoBinding.inflate(inflater, container, false);

        binding.edtQuery.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    doSearch();
                    return true;
                }
                return false;
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSearch();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(mRepoPageAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(RepoViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.getRepos().observe(this, new Observer<Resource<PagedList<Repo>>>() {
            @Override
            public void onChanged(@Nullable Resource<PagedList<Repo>> resource) {
                mRepoPageAdapter.submitList(resource.data);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.dispose();
    }

    private void doSearch() {
        String query = binding.edtQuery.getText().toString();
        viewModel.searchRepo(query);
        dismissKeyboard();
    }

    private void dismissKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void flatMapExample() {
        String query = "android";
        mCompositeDisposable.add(viewModel.searchRepoRX(query)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Response<RepoSearchResponse>, ObservableSource<Repo>>() {
                    @Override
                    public ObservableSource<Repo> apply(Response<RepoSearchResponse> response) throws Exception {
                        List<Repo> repos = response.body().getItems();
                        return Observable.fromIterable(repos);
                    }
                })
                .flatMap(new Function<Repo, ObservableSource<Response<User>>>() {
                    @Override
                    public ObservableSource<Response<User>> apply(Repo repo) throws Exception {
                        return viewModel.getUser(repo.owner.login);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<User>>() {
                    @Override
                    public void onNext(Response<User> response) {
                        Log.d(TAG, "name " + response.body().name);
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                }));
    }
    private void zipExample() {
        Observable.zip(viewModel.searchRepoRX("google"), viewModel.getUser("google"),
                new BiFunction<Response<RepoSearchResponse>, Response<User>, RepoSearchResponseAndUser>() {
                    @Override
                    public RepoSearchResponseAndUser apply(Response<RepoSearchResponse> response,
                                                           Response<User> response2) throws Exception {
                        RepoSearchResponse repoSearchResponse = response.body();
                        User user = response2.body();
                        return new RepoSearchResponseAndUser(repoSearchResponse, user);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<RepoSearchResponseAndUser>() {
                    @Override
                    public void onNext(RepoSearchResponseAndUser repoSearchResponseAndUser) {
                        Log.d(TAG, "RepoSearchResponseAndUser name " + (repoSearchResponseAndUser.user == null ? "" : repoSearchResponseAndUser.user.name));
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void rxDoSearch() {
        binding.recyclerView.setAdapter(mRepoAdapter);
        String query = binding.edtQuery.getText().toString();
        mCompositeDisposable.add(viewModel.rxSearch(query)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<RepoSearchResult, Publisher<List<Repo>>>() {
                    @Override
                    public Publisher<List<Repo>> apply(RepoSearchResult result) throws Exception {
                        Log.d(TAG, "apply " + result.totalCount);
                        return viewModel.rxLoadById(result.repoIds);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<List<Repo>>() {
                    @Override
                    public void onNext(List<Repo> repos) {
                        mRepoAdapter.swapItems(repos);
                        Log.d(TAG, "onNext " + repos.size());
                    }
                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError " + t.toString());
                    }
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete " );
                    }
                })
        );
        dismissKeyboard();
    }
    private void syncQueryExample() {
        RepoSearchResult result = viewModel.rxSearchSync("android")
                .subscribeOn(Schedulers.io())
                .blockingGet();
    }

}

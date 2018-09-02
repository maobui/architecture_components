package com.me.bui.architecturecomponents.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.text.TextUtils;

import com.me.bui.architecturecomponents.data.DataModel;
import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.util.AbsentLiveData;

import java.util.List;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class RepoViewModel extends ViewModel {

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private final MutableLiveData<String> query = new MutableLiveData<>();

    private final LiveData<List<Repo>> repos;

    private DataModel mDataModel;

    public RepoViewModel(DataModel dataModel) {
        super();
        mDataModel = dataModel;
        repos = Transformations.switchMap(query, new Function<String, LiveData<List<Repo>>>() {
            @Override
            public LiveData<List<Repo>> apply(String input) {
                if(TextUtils.isEmpty(input)) {
                    return AbsentLiveData.create();
                } else {
                    return mDataModel.searchRepo(input);
                }
            }
        });
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }

    public void searchRepo(String input) {
        query.setValue(input);
    }
}

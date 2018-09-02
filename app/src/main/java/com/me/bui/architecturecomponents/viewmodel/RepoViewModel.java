package com.me.bui.architecturecomponents.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.me.bui.architecturecomponents.data.DataModel;
import com.me.bui.architecturecomponents.data.model.Repo;

import java.util.List;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class RepoViewModel extends ViewModel {

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();

    private DataModel mDataModel;

    public RepoViewModel(DataModel dataModel) {
        super();
        mDataModel = dataModel;
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }

    public void searchRepo(String query) {

        isLoading.set(true);

        mDataModel.searchRepo(query, new DataModel.onDataReadyCallback() {
            @Override
            public void onDataReady(List<Repo> data) {
                repos.setValue(data);
                isLoading.set(false);
            }
        });
    }
}

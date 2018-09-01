package com.me.bui.architecturecomponents;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class MainViewModel extends ViewModel {

    public ObservableField<String> mData = new ObservableField<>();

    public ObservableBoolean isLoading = new ObservableBoolean(false);

    private DataModel mDataModel;

    public MainViewModel(DataModel dataModel) {
        super();
        mDataModel = dataModel;
    }

    public void refresh() {
        isLoading.set(true);
        mDataModel.retrieveData(new DataModel.onDataReadyCallback() {
            @Override
            public void onDataReady(String data) {
                mData.set(data);
                isLoading.set(false);
            }
        });
    }

    static class MainViewModelFactory implements ViewModelProvider.Factory {

        private DataModel mDataModel;

        public MainViewModelFactory(DataModel dataModel) {
            mDataModel = dataModel;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if(modelClass.isAssignableFrom(MainViewModel.class)) {
                return (T) new MainViewModel(mDataModel);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

package com.me.bui.architecturecomponents;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class MainViewModel {

    public ObservableField<String> mData = new ObservableField<>();

    public ObservableBoolean isLoading = new ObservableBoolean(false);

    private DataModel mDataModel = new DataModel();

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
}

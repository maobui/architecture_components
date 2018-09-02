package com.me.bui.architecturecomponents.util;

import android.arch.lifecycle.LiveData;

/**
 * Created by mao.bui on 9/3/2018.
 *
 * A LiveData class that has {@code null} value.
 */
public class AbsentLiveData extends LiveData {

    private AbsentLiveData() {
        postValue(null);
    }
    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}

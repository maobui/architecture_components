package com.me.bui.architecturecomponents.util

import android.arch.lifecycle.LiveData

/**
 * Created by mao.bui on 9/3/2018.
 *
 * A LiveData class that has `null` value.
 */
class AbsentLiveData<T> private constructor() : LiveData<T>() {

    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {

            return AbsentLiveData()
        }
    }
}

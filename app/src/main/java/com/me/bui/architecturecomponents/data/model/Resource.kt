package com.me.bui.architecturecomponents.data.model

import java.util.Objects

import com.me.bui.architecturecomponents.data.model.Status.ERROR
import com.me.bui.architecturecomponents.data.model.Status.LOADING
import com.me.bui.architecturecomponents.data.model.Status.SUCCESS

/**
 * Created by mao.bui on 9/3/2018.
 */
data class Resource<T>(val status: Status, val data: T?, val message: String?) {
 

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(data: T?, msg: String): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}

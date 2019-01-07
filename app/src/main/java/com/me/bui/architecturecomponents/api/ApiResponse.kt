package com.me.bui.architecturecomponents.api

import android.util.Log

import java.io.IOException

import retrofit2.Response

/**
 * Created by mao.bui on 9/3/2018.
 */
class ApiResponse<T> {
    val code: Int

    val body: T?

    val errorMessage: String?

    val isSuccessful: Boolean
        get() = code >= 200 && code < 300

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()!!.string()
                } catch (ignored: IOException) {
                    Log.e("ignored", "error while parsing response")
                }

            }
            if (message == null || message.trim { it <= ' ' }.length == 0) {
                message = response.message()
            }
            errorMessage = message
            body = null
        }
    }
}

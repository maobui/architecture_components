package com.me.bui.architecturecomponents.data.model

import com.google.gson.annotations.SerializedName

import java.util.Objects

/**
 * Created by mao.bui on 9/1/2018.
 */
data class Owner(@field:SerializedName("login")
                 val login: String,
                 @field:SerializedName("avatar_url")
                 val avatarUrl: String?,
                 @field:SerializedName("url")
                 val url: String?)
package com.me.bui.architecturecomponents.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by mao.bui on 9/19/2018.
 */
class User(@field:SerializedName("login")
           val login: String, @field:SerializedName("avatar_url")
           val avatarUrl: String, @field:SerializedName("name")
           val name: String, @field:SerializedName("company")
           val company: String,
           @field:SerializedName("repos_url")
           val reposUrl: String, @field:SerializedName("blog")
           val blog: String)

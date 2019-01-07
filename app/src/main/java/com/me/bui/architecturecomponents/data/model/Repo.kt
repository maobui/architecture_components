package com.me.bui.architecturecomponents.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index

import com.google.gson.annotations.SerializedName

/**
 * Created by mao.bui on 9/1/2018.
 */

@Entity(indices = [(Index("id")), (Index("owner_login"))],
        primaryKeys = ["name", "owner_login"])
class Repo(val id: Int,
           @field:SerializedName("name")
           val name: String,
           @field:SerializedName("full_name")
           val fullName: String? = "",
           @field:SerializedName("description")
           val description: String? = "",
           @field:SerializedName("stargazers_count")
           val stars: Int,
           var html_url: String? = "",
           @field:SerializedName("owner")
           @field:Embedded(prefix = "owner_")
           val owner: Owner)
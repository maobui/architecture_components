package com.me.bui.architecturecomponents.api

import com.google.gson.annotations.SerializedName
import com.me.bui.architecturecomponents.data.model.Repo

import java.util.ArrayList

/**
 * Created by mao.bui on 9/1/2018.
 */
class RepoSearchResponse {
    @SerializedName("total_count")
    var total: Int = 0
    @SerializedName("items")
    var items: List<Repo>? = null

    val repoIds: List<Int>
        get() {
            val repoIds = ArrayList<Int>()
            for (repo in items!!) {
                repoIds.add(repo.id)
            }
            return repoIds
        }
}

package com.me.bui.architecturecomponents.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters

import com.me.bui.architecturecomponents.data.db.GithubTypeConverters

/**
 * Created by mao.bui on 9/3/2018.
 */

@Entity
@TypeConverters(GithubTypeConverters::class)
class RepoSearchResult(@field:PrimaryKey
                       val query: String, val repoIds: List<Int>?, val totalCount: Int)

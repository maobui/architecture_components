package com.me.bui.architecturecomponents.ui

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.me.bui.architecturecomponents.data.model.Repo
import com.me.bui.architecturecomponents.databinding.ItemRepoBinding

import java.util.Objects


/**
 * Created by mao.bui on 9/1/2018.
 */
class RepoPageAdapter : PagedListAdapter<Repo, RepoPageAdapter.RepoViewHolder>(DIFF_CALLBACK) {

    inner class RepoViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            binding.repo = repo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(layoutInflater, parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        if (repo != null) {
            holder.bind(repo)
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldRepo: Repo, newRepo: Repo): Boolean {
                return oldRepo.id == newRepo.id
            }

            override fun areContentsTheSame(oldRepo: Repo, newRepo: Repo): Boolean {
                return oldRepo.name == newRepo.name && oldRepo.description == newRepo.description
            }
        }
    }
}

package com.me.bui.architecturecomponents.ui;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.databinding.ItemRepoBinding;

import java.util.Objects;


/**
 * Created by mao.bui on 9/1/2018.
 */
public class RepoPageAdapter extends PagedListAdapter<Repo, RepoPageAdapter.RepoViewHolder> {

    RepoPageAdapter() {
        super(DIFF_CALLBACK);
    }

    class RepoViewHolder extends RecyclerView.ViewHolder{

        private ItemRepoBinding binding;

        RepoViewHolder(ItemRepoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind( Repo repo) {
            binding.setRepo(repo);
            binding.executePendingBindings();
        }
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRepoBinding binding = ItemRepoBinding.inflate(layoutInflater, parent, false);
        return new RepoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        Repo repo = getItem(position);
        holder.bind(repo);
    }

    private static final DiffUtil.ItemCallback<Repo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Repo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Repo oldRepo, @NonNull Repo newRepo) {
            return Objects.equals(oldRepo.id, newRepo.id);
        }
        @Override
        public boolean areContentsTheSame(@NonNull Repo oldRepo, @NonNull Repo newRepo) {
            return Objects.equals(oldRepo.name, newRepo.name) &&
                    Objects.equals(oldRepo.description, newRepo.description);
        }
    };
}

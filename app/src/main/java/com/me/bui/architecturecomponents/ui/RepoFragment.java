package com.me.bui.architecturecomponents.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.Resource;
import com.me.bui.architecturecomponents.databinding.FragmentRepoBinding;
import com.me.bui.architecturecomponents.di.Injectable;
import com.me.bui.architecturecomponents.viewmodel.RepoViewModel;

import javax.inject.Inject;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class RepoFragment extends Fragment implements Injectable{

    public static final String TAG = RepoFragment.class.getSimpleName();


    private FragmentRepoBinding binding;

    @Inject
    ViewModelProvider.Factory factory;
    private RepoViewModel viewModel;

    private RepoAdapter repoAdapter = new RepoAdapter();

    public static RepoFragment newInstance() {
        return new RepoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRepoBinding.inflate(inflater, container, false);

        binding.edtQuery.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    doSearch();
                    return true;
                }
                return false;
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSearch();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(repoAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(RepoViewModel.class);

        viewModel.getRepos().observe(this, new Observer<Resource<PagedList<Repo>>>() {
            @Override
            public void onChanged(@Nullable Resource<PagedList<Repo>> resource) {
                binding.setResource(resource);
                binding.executePendingBindings();
                repoAdapter.submitList(resource.data);
            }
        });
    }

    private void doSearch() {
        String query = binding.edtQuery.getText().toString();
        viewModel.searchRepo(query);
        dismissKeyboard();
    }

    private void dismissKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

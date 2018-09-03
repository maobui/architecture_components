package com.me.bui.architecturecomponents.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Toast;

import com.me.bui.architecturecomponents.api.ApiResponse;
import com.me.bui.architecturecomponents.data.model.Repo;
import com.me.bui.architecturecomponents.data.model.RepoSearchResponse;
import com.me.bui.architecturecomponents.databinding.FragmentRepoBinding;
import com.me.bui.architecturecomponents.viewmodel.GithubViewModelFactory;
import com.me.bui.architecturecomponents.viewmodel.RepoViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class RepoFragment extends Fragment {

    public static final String TAG = RepoFragment.class.getSimpleName();


    private FragmentRepoBinding binding;

    @Inject
    GithubViewModelFactory factory;
    private RepoViewModel viewModel;

    private RepoAdapter repoAdapter = new RepoAdapter(new ArrayList<Repo>());

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
        binding.setViewModel(viewModel);
        viewModel.getRepos().observe(this, new Observer<ApiResponse<RepoSearchResponse>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<RepoSearchResponse> response) {
                viewModel.isLoading.set(false);
                if(response == null) {
                    repoAdapter.swapItems(null);
                    return;
                }
                if(response.isSuccessful()) {
                    repoAdapter.swapItems(response.body.getItems());
                } else {
                    String errorMsg = response.errorMessage;
                    Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void doSearch() {
        String query = binding.edtQuery.getText().toString();
        viewModel.searchRepo(query);
        viewModel.isLoading.set(true);
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

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}

package com.me.bui.architecturecomponents;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.me.bui.architecturecomponents.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel.MainViewModelFactory factory = new MainViewModel.MainViewModelFactory(new DataModel());
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        mBinding.setViewModel(mViewModel);
    }
}

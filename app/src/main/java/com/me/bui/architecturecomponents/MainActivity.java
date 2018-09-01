package com.me.bui.architecturecomponents;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        mViewModel.mData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mBinding.txtHelloWord.setText(s);
            }
        });

        mViewModel.toasText.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

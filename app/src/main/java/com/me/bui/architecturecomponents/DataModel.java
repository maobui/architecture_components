package com.me.bui.architecturecomponents;

import android.os.Handler;

/**
 * Created by mao.bui on 9/1/2018.
 */
public class DataModel {

    public void retrieveData(final onDataReadyCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onDataReady("New Data");
            }
        }, 1500);
    }

    interface onDataReadyCallback {
        void onDataReady(String data);
    }
}

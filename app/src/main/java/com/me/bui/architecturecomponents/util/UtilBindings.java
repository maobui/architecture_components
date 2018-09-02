package com.me.bui.architecturecomponents.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by mao.bui on 9/3/2018.
 */
public class UtilBindings {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("imageUrl")
    public static void bindImage(ImageView imageView, String url) {
        Context  context = imageView.getContext();
        Glide.with(context)
                .load(url)
                .into(imageView);
    }
}

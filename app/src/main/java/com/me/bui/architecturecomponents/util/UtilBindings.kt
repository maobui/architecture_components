package com.me.bui.architecturecomponents.util

import android.databinding.BindingAdapter
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by mao.bui on 9/3/2018.
 */
object UtilBindings {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView?, url: String?) {
        val context = imageView!!.context
        Log.d("URL" , " $url")
        Glide.with(context)
                .load(url)
                .into(imageView)
    }
}

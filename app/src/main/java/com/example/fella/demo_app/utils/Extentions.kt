@file:JvmName("ExtensionsUtils")

package com.example.fella.demo_app.utils

import android.graphics.Bitmap
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.fella.demo_app.R
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(imageUrl:String) {
    if (TextUtils.isEmpty(imageUrl)) {
        Picasso.get().
                load(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(this)
    } else {
        Picasso.get()
                .load(imageUrl)
                .config(Bitmap.Config.RGB_565)
                .fit()
                .centerCrop()
                .into(this)
    }
}

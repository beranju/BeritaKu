package com.nextgen.beritaku.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nextgen.beritaku.core.R

object ExtentionFun {
    fun ImageView.loadImage(url: String?) = Glide.with(this).load(url)
        .apply(RequestOptions().placeholder(R.drawable.ic_load_image).error(R.drawable.ic_empty_image)).into(this)
}
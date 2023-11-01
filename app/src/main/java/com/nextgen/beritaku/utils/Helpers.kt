package com.nextgen.beritaku.utils

import android.util.Patterns
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nextgen.beritaku.R

fun String.isEmailValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun ImageView.loadImage(url: String) = Glide.with(this.context).load(url)
    .apply(
        RequestOptions().placeholder(R.drawable.ic_load_image)
            .error(R.drawable.img_koran)
    )
    .centerCrop()
    .into(this)
package com.nextgen.beritaku.core.utils

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nextgen.beritaku.core.R

object ExtentionFun {
    fun ImageView.loadImage(url: String?) = Glide.with(this).load(url)
        .apply(RequestOptions().placeholder(R.drawable.ic_load_image).error(R.drawable.ic_empty_image)).into(this)

    /**
     * this method used to resolve...
     * Error:'getParcelableExtra(String!): T?' is deprecated. Deprecated in Java
     * src: https://stackoverflow.com/a/73311814/18219793
     */
    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }


}
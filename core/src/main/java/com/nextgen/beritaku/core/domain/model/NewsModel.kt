package com.nextgen.beritaku.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.nextgen.beritaku.core.data.source.remote.response.Source
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class NewsModel(
    var publishedAt: String,
    var author: String,
    var urlToImage: String,
    var description: String,
    var source: @RawValue Source,
    var title: String,
    var url: String,
    var content: String,
    var isFavorite: Boolean
):Parcelable

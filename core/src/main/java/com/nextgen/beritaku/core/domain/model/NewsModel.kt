package com.nextgen.beritaku.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    var publishedAt: String,
    var author: String,
    var urlToImage: String,
    var description: String,
    var source: SourceModel,
    var title: String,
    var url: String,
    var content: String,
    var category: String,
    var isFavorite: Boolean
):Parcelable

@Parcelize
data class SourceModel(
    var name: String,
    var id: String
):Parcelable

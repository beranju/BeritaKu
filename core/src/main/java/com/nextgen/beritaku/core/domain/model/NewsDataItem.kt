package com.nextgen.beritaku.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsDataItem(
    val country: List<String?>? = null,
    val creator: String? = null,
    val keywords: String? = null,
    val imageUrl: String? = null,
    val link: String? = null,
    val description: String? = null,
    val language: String? = null,
    val title: String? = null,
    val pubDate: String? = null,
    val content: String? = null,
    val articleId: String? = null,
    val videoUrl: String? = null,
    val sourcePriority: Int? = null,
    val sourceId: String? = null,
    val category: List<String?>? = null
): Parcelable

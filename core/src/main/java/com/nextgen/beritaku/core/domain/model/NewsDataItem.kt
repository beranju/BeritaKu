package com.nextgen.beritaku.core.domain.model

import com.google.gson.annotations.SerializedName

data class NewsDataItem(
    val country: List<String?>? = null,
    val creator: Any? = null,
    val keywords: Any? = null,
    val imageUrl: Any? = null,
    val link: String? = null,
    val description: String? = null,
    val language: String? = null,
    val title: String? = null,
    val pubDate: String? = null,
    val content: String? = null,
    val articleId: String? = null,
    val videoUrl: Any? = null,
    val sourcePriority: Int? = null,
    val sourceId: String? = null,
    val category: List<String?>? = null
)

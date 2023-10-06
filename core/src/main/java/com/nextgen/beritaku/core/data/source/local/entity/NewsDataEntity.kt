package com.nextgen.beritaku.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "newsData")
data class NewsDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "articleId")
    val articleId: String,
    @ColumnInfo(name = "creator")
    val creator: String? = null,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String? = null,
    @ColumnInfo(name = "link")
    val link: String? = null,
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "title")
    val title: String? = null,
    @ColumnInfo(name = "pubDate")
    val pubDate: String? = null,
    @ColumnInfo(name = "content")
    val content: String? = null,
    @ColumnInfo(name = "sourceId")
    val sourceId: String? = null,
    @ColumnInfo(name = "category")
    val category: List<String?>? = null
) : Parcelable

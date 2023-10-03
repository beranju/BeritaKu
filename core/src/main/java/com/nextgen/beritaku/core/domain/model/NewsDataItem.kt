package com.nextgen.beritaku.core.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(country)
        parcel.writeString(creator)
        parcel.writeString(keywords)
        parcel.writeString(imageUrl)
        parcel.writeString(link)
        parcel.writeString(description)
        parcel.writeString(language)
        parcel.writeString(title)
        parcel.writeString(pubDate)
        parcel.writeString(content)
        parcel.writeString(articleId)
        parcel.writeString(videoUrl)
        parcel.writeValue(sourcePriority)
        parcel.writeString(sourceId)
        parcel.writeStringList(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsDataItem> {
        override fun createFromParcel(parcel: Parcel): NewsDataItem {
            return NewsDataItem(parcel)
        }

        override fun newArray(size: Int): Array<NewsDataItem?> {
            return arrayOfNulls(size)
        }
    }
}

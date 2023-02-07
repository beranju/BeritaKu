package com.nextgen.beritaku.core.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nextgen.beritaku.core.data.source.remote.response.Source
import com.nextgen.beritaku.core.domain.model.NewsModel

object ObjectConverter {
    @JvmStatic
    fun toNewsModel(value: String?): NewsModel{
        val modelType = object: TypeToken<NewsModel?>(){}.type
        return Gson().fromJson(value, modelType)
    }

    @JvmStatic
    fun fromNewsModel(value: NewsModel): String{
        val gson = Gson()
        return gson.toJson(value)
    }
}
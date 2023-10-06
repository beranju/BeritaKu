package com.nextgen.beritaku.core.utils

import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.model.SourceModel

object DataDummy {

    fun generateListNewsModel(): List<NewsModel>{
        val data = ArrayList<NewsModel>()
        for (i in 1..5){
            val list =
                NewsModel(i.toString(), i.toString(),i.toString(),i.toString(), SourceModel(
                    i.toString(),
                    i.toString(),
                ),i.toString(),i.toString(),i.toString(),i.toString(), false)
            data.add(list)
        }
        return data
    }
    fun generateListNewsData(): List<NewsDataItem>{
        val data = ArrayList<NewsDataItem>()
        for (i in 1..5){
            val list =
                NewsDataItem(articleId = i.toString())
            data.add(list)
        }
        return data
    }
}
package com.nextgen.beritaku.core.domain.repository

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getAllNews(category: String, query: String?, pageSize: Int?): Flow<Resource<List<NewsModel>>>

//    fun getAllNewsByCategory(category: String, query: String?): Flow<Resource<List<NewsModel>>>

    fun getFavoriteNews(): Flow<List<NewsModel>>

    fun setFavoriteNews(newsModel: NewsModel, state: Boolean)
}
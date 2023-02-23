package com.nextgen.beritaku.core.domain.repository

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getAllNews(): Flow<Resource<List<NewsModel>>>

    fun getAllNewsByCategory(category: String, query: String?): Flow<Resource<List<NewsModel>>>

    fun getNewsByQuery(query: String): Flow<Resource<List<NewsModel>>>

    fun getFavoriteNews(): Flow<Resource<List<NewsModel>>>

    suspend fun isFavoriteNews(publishAt: String): Boolean

    suspend fun insertFavoriteNews(news: NewsModel)

    suspend fun deleteNews(news: NewsModel)
}
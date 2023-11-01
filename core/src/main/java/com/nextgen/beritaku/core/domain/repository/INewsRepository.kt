package com.nextgen.beritaku.core.domain.repository

import com.nextgen.beritaku.core.data.source.Resource
import kotlinx.coroutines.flow.Flow

interface INewsRepository<T> {
    fun getAllNews(): Flow<Resource<List<T>>>

    fun getAllNewsByCategory(category: String?, query: String?): Flow<Resource<List<T>>>

    fun getNewsByQuery(query: String): Flow<Resource<List<T>>>

    fun getFavoriteNews(): Flow<Resource<List<T>>>

    suspend fun isFavoriteNews(id: String): Boolean

    suspend fun insertFavoriteNews(news: T)

    suspend fun deleteNews(news: T)
}
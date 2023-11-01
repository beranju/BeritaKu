package com.nextgen.beritaku.core.domain.usecase

import com.nextgen.beritaku.core.data.source.Resource
import kotlinx.coroutines.flow.Flow

interface NewsUseCase<T> {
    fun getAllNews(): Flow<Resource<List<T>>>
    fun getAllNewsByCategory(category: String?, query: String?): Flow<Resource<List<T>>>

    fun searchNews(query: String): Flow<Resource<List<T>>>

    fun getFavoriteNews(): Flow<Resource<List<T>>>

    suspend fun isFavoriteNews(id: String): Boolean

    suspend fun insertFavoriteNews(news: T)

    suspend fun deleteNews(news: T)
}
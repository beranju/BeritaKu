package com.nextgen.beritaku.core.data.source.local

import com.nextgen.beritaku.core.data.source.local.entity.NewsEntity
import com.nextgen.beritaku.core.data.source.local.room.NewsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn


class LocalDataSource (private val newsDao: NewsDao) {
    fun getAllNews(): Flow<List<NewsEntity>> =newsDao.getAllNews()

    fun getAllNewsByCategory(category: String): Flow<List<NewsEntity>> =newsDao.getAllNewsByCategory(category)

    fun getFavoriteNews(): Flow<List<NewsEntity>> = newsDao.getFavoriteNews()

    fun getSearchNews(query: String) : Flow<List<NewsEntity>> = newsDao.getSearchNews(query).flowOn(Dispatchers.Default)

    suspend fun insertNews(news: List<NewsEntity>) = newsDao.insertNews(news)

    fun setFavoriteNews(newsEntity: NewsEntity, state: Boolean){
        newsEntity.isFavorite = state
        newsDao.updateNews(newsEntity)
    }
}
package com.nextgen.beritaku.core.data.source.local

import com.nextgen.beritaku.core.data.source.local.entity.NewsEntity
import com.nextgen.beritaku.core.data.source.local.room.NewsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val newsDao: NewsDao) {
    fun getAllNews(): Flow<List<NewsEntity>> =newsDao.getAllNews()

    fun getFavoriteNews(): Flow<List<NewsEntity>> = newsDao.getFavoriteNews()

    suspend fun insertNews(news: List<NewsEntity>) = newsDao.insertNews(news)

    fun setFavoriteNews(newsEntity: NewsEntity, state: Boolean){
        newsEntity.isFavorite = state
        newsDao.updateNews(newsEntity)
    }
}
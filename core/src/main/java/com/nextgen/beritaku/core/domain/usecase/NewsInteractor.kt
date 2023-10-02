package com.nextgen.beritaku.core.domain.usecase

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class NewsInteractor (private val newsRepository: INewsRepository): NewsUseCase {
    override fun getAllNews(): Flow<Resource<List<NewsDataItem>>> = newsRepository.getAllNews()
    override fun getAllNewsByCategory(
        category: String?,
        query: String?
    ): Flow<Resource<List<NewsDataItem>>> = newsRepository.getAllNewsByCategory(category, query)
//
//    override fun searchNews(query: String): Flow<Resource<List<NewsModel>>> = newsRepository.getNewsByQuery(query)
//
//    override fun getFavoriteNews(): Flow<Resource<List<NewsModel>>> = newsRepository.getFavoriteNews()
//
//    override suspend fun isFavoriteNews(publishAt: String): Boolean = newsRepository.isFavoriteNews(publishAt)
//
//    override suspend fun insertFavoriteNews(news: NewsModel) = newsRepository.insertFavoriteNews(news)
//
//    override suspend fun deleteNews(news: NewsModel) = newsRepository.deleteNews(news)

}
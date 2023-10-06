package com.nextgen.beritaku.core.domain.usecase

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class NewsDataInteractor(private val newsRepository: INewsRepository<NewsDataItem>) :
    NewsUseCase<NewsDataItem> {
    override fun getAllNews(): Flow<Resource<List<NewsDataItem>>> = newsRepository.getAllNews()
    override fun getAllNewsByCategory(
        category: String?,
        query: String?
    ): Flow<Resource<List<NewsDataItem>>> = newsRepository.getAllNewsByCategory(category, query)

    override fun searchNews(query: String): Flow<Resource<List<NewsDataItem>>> =
        newsRepository.getNewsByQuery(query)

    override fun getFavoriteNews(): Flow<Resource<List<NewsDataItem>>> =
        newsRepository.getFavoriteNews()

    override suspend fun isFavoriteNews(id: String): Boolean = newsRepository.isFavoriteNews(id)

    override suspend fun insertFavoriteNews(news: NewsDataItem) =
        newsRepository.insertFavoriteNews(news)

    override suspend fun deleteNews(news: NewsDataItem) = newsRepository.deleteNews(news)

}
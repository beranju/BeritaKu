package com.nextgen.beritaku.core.domain.usecase

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsInteractor @Inject constructor(private val newsRepository: INewsRepository): NewsUseCase {
    override fun getAllNews(): Flow<Resource<List<NewsModel>>> = newsRepository.getAllNews()

    override fun getAllNewsByCategory(
        category: String,
        query: String?,
    ): Flow<Resource<List<NewsModel>>> = newsRepository.getAllNewsByCategory(category, query)

    override fun searchNews(query: String?): Flow<Resource<List<NewsModel>>> = newsRepository.searchNews(query)

    override fun getFavoriteNews(): Flow<List<NewsModel>> =
        newsRepository.getFavoriteNews()

    override fun setFavoriteNews(news: NewsModel, state: Boolean) =
        newsRepository.setFavoriteNews(news, state)
}
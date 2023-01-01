package com.nextgen.beritaku.core.domain.usecase

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsInteractor @Inject constructor(private val newsRepository: INewsRepository): NewsUseCase {
    override fun getAllNews(category: String, query: String?, pageSize: Int?): Flow<Resource<List<NewsModel>>> =
        newsRepository.getAllNews(category, query, pageSize)

    override fun getFavoriteNews(): Flow<List<NewsModel>> =
        newsRepository.getFavoriteNews()

    override fun setFavoriteNews(news: NewsModel, state: Boolean) =
        newsRepository.setFavoriteNews(news, state)
}
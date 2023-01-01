package com.nextgen.beritaku.core.domain.usecase

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getAllNews(category: String, query: String?): Flow<Resource<List<NewsModel>>>

    fun getFavoriteNews(): Flow<List<NewsModel>>

    fun setFavoriteNews(news: NewsModel, state: Boolean)
}
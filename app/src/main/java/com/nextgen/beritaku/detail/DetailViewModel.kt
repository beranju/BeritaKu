package com.nextgen.beritaku.detail

import androidx.lifecycle.ViewModel
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase

class DetailViewModel (private val newsUseCase: NewsUseCase): ViewModel() {
    fun setFavoriteNews(news: NewsModel, state: Boolean) = newsUseCase.setFavoriteNews(news, state)
}
package com.nextgen.beritaku.detail

import androidx.lifecycle.ViewModel
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val newsUseCase: NewsUseCase): ViewModel() {
    fun setFavoriteNews(news: NewsModel, state: Boolean) = newsUseCase.setFavoriteNews(news, state)
}
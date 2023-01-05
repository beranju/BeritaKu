package com.nextgen.beritaku.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase


class FavoriteViewModel (private val newsUseCase: NewsUseCase): ViewModel() {
    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()
}
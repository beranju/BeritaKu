package com.nextgen.beritaku.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase


class FavoriteViewModel (newsUseCase: NewsUseCase): ViewModel() {
    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()
}
package com.nextgen.beritaku.favorite

import androidx.lifecycle.ViewModel
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase


class FavoriteViewModel(newsUseCase: NewsUseCase<NewsDataItem>) : ViewModel() {
//    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()
}
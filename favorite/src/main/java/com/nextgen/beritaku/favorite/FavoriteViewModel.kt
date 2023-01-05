package com.nextgen.beritaku.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class FavoriteViewModel @Inject constructor (private val newsUseCase: NewsUseCase): ViewModel() {
    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()
}
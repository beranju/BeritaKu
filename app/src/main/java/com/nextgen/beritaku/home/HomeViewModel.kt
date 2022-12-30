package com.nextgen.beritaku.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsUseCase: NewsUseCase): ViewModel() {
//    val headlineNews = newsUseCase.getAllNews()

    fun headlineNews(category: String, query: String ) = newsUseCase.getAllNews(category, query).asLiveData()
}
package com.nextgen.beritaku.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase

class ExploreViewModel (private val newsUseCase: NewsUseCase): ViewModel() {

    fun searchNews(query: String) =
        newsUseCase.searchNews(query).asLiveData()

    fun exploreNews(category: String, query: String?) =
        newsUseCase.getAllNewsByCategory(category, query).asLiveData()

}
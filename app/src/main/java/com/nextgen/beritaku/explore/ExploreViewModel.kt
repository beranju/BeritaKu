package com.nextgen.beritaku.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor (private val newsUseCase: NewsUseCase): ViewModel() {
    fun exploreNews(category: String, query: String?, pageSize: Int?) =
        newsUseCase.getAllNews(category, query, pageSize).asLiveData()

}
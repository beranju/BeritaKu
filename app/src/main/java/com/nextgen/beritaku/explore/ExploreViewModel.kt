package com.nextgen.beritaku.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor (private val newsUseCase: NewsUseCase): ViewModel() {

    var querySearch = MutableStateFlow("")

    val searchResult = querySearch

    fun exploreNews(category: String, query: String?) =
        newsUseCase.getAllNewsByCategory(category, query).asLiveData()

}
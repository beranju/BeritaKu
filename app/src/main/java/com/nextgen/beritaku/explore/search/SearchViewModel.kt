package com.nextgen.beritaku.explore.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.UiState
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel (private val newsUseCase: NewsUseCase): ViewModel() {

    val searchQuery = MutableStateFlow("")


    val searchResult = searchQuery
        .debounce(200)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            newsUseCase.searchNews(it)
        }.asLiveData()



}
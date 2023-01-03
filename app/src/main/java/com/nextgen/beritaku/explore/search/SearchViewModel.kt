package com.nextgen.beritaku.explore.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val newsUseCase: NewsUseCase): ViewModel() {
    val searchQuery = MutableStateFlow("")


    val searchResult = searchQuery
        .debounce(200)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            newsUseCase.searchNews(it).asLiveData()
        }.asLiveData()

}

sealed class SearchSealed
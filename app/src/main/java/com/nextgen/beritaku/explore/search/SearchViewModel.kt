package com.nextgen.beritaku.explore.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class SearchViewModel (private val newsUseCase: NewsUseCase): ViewModel() {

    val searchQuery = MutableStateFlow("")

//    val searchResult = searchQuery
//        .debounce(200)
//        .distinctUntilChanged()
//        .filter {
//            it.trim().isNotEmpty()
//        }
//        .mapLatest {
//            newsUseCase.searchNews(it)
//        }.flowOn(Dispatchers.Default).asLiveData()



}
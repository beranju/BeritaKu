package com.nextgen.beritaku.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import com.nextgen.beritaku.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel (private val newsUseCase: NewsUseCase): ViewModel() {

//    private var _stateUi = MutableStateFlow<UiState<List<NewsModel>>>(UiState.Loading)
//    val state: StateFlow<UiState<List<NewsModel>>> get() = _stateUi
//
//    fun fetchNews(){
//        viewModelScope.launch {
//            newsUseCase.getAllNews().collect{resource ->
//                _stateUi.value = when(resource){
//                    is Resource.Loading -> UiState.Loading
//                    is Resource.Success -> UiState.Success(resource.data!!)
//                    is Resource.Error -> UiState.Error(resource.message!!)
//                }
//            }
//        }
//    }

    private var _topNews: MutableLiveData<NewsModel> = MutableLiveData()
    val topNews: LiveData<NewsModel> get() = _topNews

    init {
        topHeadline()
    }

    fun headlineNews() = newsUseCase.getAllNews().asLiveData()

    private fun topHeadline() =
        viewModelScope.launch {
            newsUseCase.getAllNews()
                .collect{
                    if (it is Resource.Success){
                        _topNews.value = it.data!!.randomOrNull()
                    }
                }
        }
}
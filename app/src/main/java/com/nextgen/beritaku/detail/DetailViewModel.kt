package com.nextgen.beritaku.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import com.nextgen.beritaku.di.useCaseModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel (private val newsUseCase: NewsUseCase): ViewModel() {
    private var _isFavorite: MutableLiveData<Boolean?> = MutableLiveData()
    val isFavorite: LiveData<Boolean?> get() =  _isFavorite

    fun setFavoriteNews(news: NewsModel) {
        viewModelScope.launch {
            if (_isFavorite.value == true){
                newsUseCase.deleteNews(news)
            }else{
                newsUseCase.insertFavoriteNews(news)
            }
            isFavoriteNews(news.publishedAt)
        }
    }

    fun isFavoriteNews(publishAt: String) {
        viewModelScope.launch {
            _isFavorite.value = newsUseCase.isFavoriteNews(publishAt)
        }
    }
}
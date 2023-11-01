package com.nextgen.beritaku.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val newsUseCase: NewsUseCase<NewsDataItem>) : ViewModel() {
    private var _isFavorite: MutableLiveData<Boolean?> = MutableLiveData()
    val isFavorite: LiveData<Boolean?> get() = _isFavorite

    fun setFavoriteNews(news: NewsDataItem) {
        viewModelScope.launch {
            if (_isFavorite.value == true){
                newsUseCase.deleteNews(news)
            }else{
                newsUseCase.insertFavoriteNews(news)
            }
            isFavoriteNews(news.articleId.orEmpty())
        }
    }

    fun isFavoriteNews(id: String) {
        viewModelScope.launch {
            _isFavorite.value = newsUseCase.isFavoriteNews(id)
        }
    }
}
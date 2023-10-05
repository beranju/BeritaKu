package com.nextgen.beritaku.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase

class DetailViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {
    private var _isFavorite: MutableLiveData<Boolean?> = MutableLiveData()
    val isFavorite: LiveData<Boolean?> get() = _isFavorite

//    fun setFavoriteNews(news: NewsModel) {
//        viewModelScope.launch {
//            if (_isFavorite.value == true){
//                newsUseCase.deleteNews(news)
//            }else{
//                newsUseCase.insertFavoriteNews(news)
//            }
//            isFavoriteNews(news.publishedAt)
//        }
//    }

//    fun isFavoriteNews(publishAt: String) {
//        viewModelScope.launch {
//            _isFavorite.value = newsUseCase.isFavoriteNews(publishAt)
//        }
//    }
}
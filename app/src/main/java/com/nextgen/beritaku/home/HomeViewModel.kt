package com.nextgen.beritaku.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.launch

class HomeViewModel (private val newsUseCase: NewsUseCase): ViewModel() {

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
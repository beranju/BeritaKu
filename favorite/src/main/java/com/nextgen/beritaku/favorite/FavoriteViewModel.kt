package com.nextgen.beritaku.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class FavoriteViewModel(private val newsUseCase: NewsUseCase<NewsDataItem>) : ViewModel() {

    private var _news: MutableLiveData<List<NewsDataItem>> = MutableLiveData()
    val news: LiveData<List<NewsDataItem>> get() = _news

    private var _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private var _error: MutableLiveData<String?> = MutableLiveData(null)
    val error: LiveData<String?> get() = _error

    init {
        getAllFavorite()
    }

    private fun getAllFavorite() {
        viewModelScope.launch {
            newsUseCase.getFavoriteNews().collect{result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data!!.isNotEmpty()) {
                            _news.postValue(result.data!!)
                        } else {
                            _news.value = emptyList()
                        }
                        _loading.value = false
                        _error.value = null
                    }

                    is Resource.Loading -> {
                        _loading.value = true
                        _error.value = null
                    }

                    is Resource.Error -> {
                        _error.value = result.message.orEmpty()
                        _loading.value = false
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}
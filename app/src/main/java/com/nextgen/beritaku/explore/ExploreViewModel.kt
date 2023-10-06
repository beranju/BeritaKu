package com.nextgen.beritaku.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import com.nextgen.beritaku.core.utils.Constants.TOP_CATEGORY
import kotlinx.coroutines.launch

class ExploreViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    private var _news: MutableLiveData<List<NewsDataItem>> = MutableLiveData()
    val news: LiveData<List<NewsDataItem>> get() = _news

    private var _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private var _error: MutableLiveData<String?> = MutableLiveData(null)
    val error: LiveData<String?> get() = _error

    init {
        fetchNews(category = TOP_CATEGORY)
    }

    fun fetchNews(category: String?, query: String? = null) {
        viewModelScope.launch {
            newsUseCase.getAllNewsByCategory(category, query)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _loading.value = true
                        }

                        is Resource.Error -> {
                            _loading.value = false
                            _error.value = result.message
                        }

                        is Resource.Success -> {
                            _loading.value = false
                            if (result.data?.isNotEmpty() == true) {
                                _news.value = result.data!!
                            } else {
                                _news.value = emptyList()
                            }
                        }
                    }
                }
        }
    }
}
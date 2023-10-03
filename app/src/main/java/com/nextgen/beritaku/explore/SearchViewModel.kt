package com.nextgen.beritaku.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.launch

class SearchViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    private var _query: MutableLiveData<String> = MutableLiveData()
    val query: LiveData<String> = _query

    private var _news: MutableLiveData<List<NewsDataItem>> = MutableLiveData()
    val news: LiveData<List<NewsDataItem>> get() = _news

    private var _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private var _error: MutableLiveData<String?> = MutableLiveData(null)
    val error: LiveData<String?> get() = _error

    fun findNewsByQuery(query: String) {
        viewModelScope.launch {
            newsUseCase.getAllNewsByCategory(category = null, query)
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
                            _news.value =
                                if (result.data?.isNotEmpty() == true) result.data!! else emptyList()
                        }
                    }
                }
        }
    }

//    val searchQuery = MutableStateFlow("")

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
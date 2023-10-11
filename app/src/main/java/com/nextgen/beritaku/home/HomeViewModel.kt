package com.nextgen.beritaku.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.data.source.repository.AuthRepository
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.repository.IAuthRepository
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val newsUseCase: NewsUseCase<NewsDataItem>,
    private val authRepository: IAuthRepository = AuthRepository()
) : ViewModel() {

    private var _topNews: MutableLiveData<List<NewsDataItem>> = MutableLiveData()
    val topNews: LiveData<List<NewsDataItem>> get() = _topNews

    private var _forYouNews: MutableLiveData<List<NewsDataItem>> = MutableLiveData()
    val forYouNews: LiveData<List<NewsDataItem>> get() = _forYouNews

    private var _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private var _error: MutableLiveData<String?> = MutableLiveData(null)
    val error: LiveData<String?> get() = _error

    val userData = authRepository.getUser()

    init {
        topHeadline()
    }

    fun topHeadline() =
        viewModelScope.launch {
            newsUseCase.getAllNews()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            Log.d("HomeFragment", "Sukses fetch data")
                            if (result.data!!.isNotEmpty()) {
                                _forYouNews.value = result.data!!
                                _topNews.value = emptyList()
                                _topNews.value = result.data!!.take(5).reversed()
                            } else {
                                _forYouNews.value = emptyList()
                            }
                            _loading.value = false
                            _error.value = null
                        }

                        is Resource.Loading -> {
                            Log.d("HomeFragment", "Loading")
                            _loading.value = true
                            _error.value = null
                        }

                        is Resource.Error -> {
                            Log.e("HomeFragment", "error: ${result.message}")
                            _error.value = result.message.orEmpty()
                            _loading.value = false
                        }
                    }
                }
        }
}
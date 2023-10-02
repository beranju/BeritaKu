package com.nextgen.beritaku.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.data.source.repository.AuthRepository
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.repository.IAuthRepository
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val newsUseCase: NewsUseCase, private val authRepository: IAuthRepository = AuthRepository()) : ViewModel() {

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

    private fun topHeadline() =
        viewModelScope.launch {
            newsUseCase.getAllNews()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (result.data!!.isNotEmpty()) {
                                _forYouNews.value = result.data!!
                                _topNews.value = result.data!!.take(5).reversed()
                            } else {
                                _forYouNews.value = emptyList()
                            }
                            _loading.value = false
                        }

                        is Resource.Loading -> {
                            _loading.value = true
                        }

                        is Resource.Error -> {
                            _error.value = result.message.orEmpty()
                            _loading.value = false
                        }
                    }
                }
        }
}
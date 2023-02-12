package com.nextgen.beritaku.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.data.source.repository.AuthRepository
import com.nextgen.beritaku.core.domain.repository.IAuthRepository
import com.nextgen.beritaku.utils.UiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: IAuthRepository = AuthRepository()): ViewModel() {
    private var _uiState = MutableLiveData<UiState<Unit>>()
    val uiState: LiveData<UiState<Unit>> get() = _uiState

    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            repository.loginWithEmail(email, password)
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect{state->
                    when(state){
                        is Resource.Loading -> _uiState.value = UiState.Loading
                        is Resource.Error -> _uiState.value = UiState.Error(state.message.toString())
                        is Resource.Success -> _uiState.value = UiState.Success(state.data!!)
                    }
                }

        }
    }

}
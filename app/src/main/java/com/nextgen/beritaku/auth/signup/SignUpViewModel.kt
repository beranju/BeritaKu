package com.nextgen.beritaku.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.data.source.repository.AuthRepository
import com.nextgen.beritaku.core.domain.repository.IAuthRepository
import com.nextgen.beritaku.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: IAuthRepository = AuthRepository()): ViewModel()  {
    private var _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiState: StateFlow<UiState<Unit>> get() = _uiState

    fun registerWithEmail(email: String, password: String, name: String){
        viewModelScope.launch {
            repository.registerWithEmail(email, password, name)
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect{
                    when(it){
                        is Resource.Success -> _uiState.value = UiState.Success(it.data!!)
                        is Resource.Error -> _uiState.value = UiState.Error(it.message.toString())
                        is Resource.Loading -> _uiState.value = UiState.Loading
                    }
                }
        }
    }
}
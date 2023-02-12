package com.nextgen.beritaku.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.data.source.repository.AuthRepository
import com.nextgen.beritaku.core.domain.repository.IAuthRepository
import com.nextgen.beritaku.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: IAuthRepository = AuthRepository()): ViewModel() {

    private var _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiState: StateFlow<UiState<Unit>> get() = _uiState

    fun updateDataUser(name: String, uri: Uri) {
        viewModelScope.launch {
            repository.updateUserData(name, uri)
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect{result->
                    when(result){
                        is Resource.Loading -> _uiState.value = UiState.Loading
                        is Resource.Error -> _uiState.value = UiState.Error(result.message.toString())
                        is Resource.Success -> _uiState.value = UiState.Success(result.data!!)
                    }
                }
        }
    }

    fun getUser() = repository.getUser()
}
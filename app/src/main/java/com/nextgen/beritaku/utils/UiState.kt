package com.nextgen.beritaku.utils

sealed class UiState<out T: Any?> {
    object Loading: UiState<Nothing>()
    data class Success<T>(val data: T): UiState<T>()
    data class Error(val message: String): UiState<Nothing>()
}



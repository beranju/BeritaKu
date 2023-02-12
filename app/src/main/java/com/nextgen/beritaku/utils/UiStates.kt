package com.nextgen.beritaku.utils

sealed class UiStates<out T: Any?>(){
    object Init: UiStates<Nothing>()
    data class Loading(val isLoading: Boolean): UiStates<Nothing>()
    data class Success<T>(val data: T): UiStates<T>()
    data class Error(val message: String): UiStates<Nothing>()
}
package com.nextgen.beritaku

import com.nextgen.beritaku.core.domain.model.NewsModel

sealed interface UiState{
    object Loading: UiState
    data class Success(val data: List<NewsModel>): UiState
    data class Error(val message: String? = null): UiState
}

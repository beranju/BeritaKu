package com.nextgen.beritaku.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase

class HomeViewModel (private val newsUseCase: NewsUseCase): ViewModel() {

    fun headlineNews() = newsUseCase.getAllNews().asLiveData()
}
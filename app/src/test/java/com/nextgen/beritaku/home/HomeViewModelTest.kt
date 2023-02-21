package com.nextgen.beritaku.home

import com.nextgen.beritaku.core.data.source.repository.NewsRepository
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.usecase.NewsInteractor
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify

class HomeViewModelTest {
    private lateinit var useCase: NewsUseCase
    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var repository: NewsRepository

    @Before
    fun setup(){
        useCase = NewsInteractor(repository)
        viewModel = HomeViewModel(useCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetch headlineNews should return success`() {
        runTest {
            doReturn(flowOf(emptyList<NewsModel>())).`when`(repository).getAllNews()
            verify(repository).getAllNews()
        }
    }
}
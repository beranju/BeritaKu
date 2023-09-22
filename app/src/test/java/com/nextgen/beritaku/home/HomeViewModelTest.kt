//package com.nextgen.beritaku.home
//
//import com.nextgen.beritaku.core.data.source.Resource
//import com.nextgen.beritaku.core.domain.model.NewsModel
//import com.nextgen.beritaku.core.domain.model.SourceModel
//import com.nextgen.beritaku.core.domain.repository.INewsRepository
//import com.nextgen.beritaku.core.domain.usecase.NewsInteractor
//import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
//import com.nextgen.beritaku.utils.UiState
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runBlockingTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Assert.*
//
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.Mockito.`when`
//import org.mockito.MockitoAnnotations
//
//class HomeViewModelTest {
//
//    private lateinit var viewModel: HomeViewModel
//    private lateinit var useCase: NewsUseCase
//
//    @Mock
//    private lateinit var repository: INewsRepository
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.openMocks(this)
//
//        useCase = NewsInteractor(repository)
//        viewModel = HomeViewModel(useCase)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `fetch news should return resources success`() = runBlockingTest{
//        val newsList = listOf(NewsModel(
//            "", "", "", "", SourceModel("", ""), "", "", "", "", false
//        ))
//        val flow = flowOf(Resource.Success(newsList))
//        `when`(repository.getAllNews()).thenReturn(flow)
//        viewModel.fetchNews()
//        assertEquals(UiState.Success(newsList), viewModel.state.first())
//    }
//}
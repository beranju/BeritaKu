package com.nextgen.beritaku.core.domain

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import com.nextgen.beritaku.core.domain.usecase.NewsInteractor
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import com.nextgen.beritaku.core.utils.DataDummy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsUseCaseTest {
    private lateinit var useCase: NewsUseCase

    @Mock
    private lateinit var newsRepository: INewsRepository

    @Before
    fun setup(){
        useCase = NewsInteractor(newsRepository)
        val expected: Flow<Resource<List<NewsModel>>> = flow {
            emit(Resource.Success(DataDummy.generateListNewsModel()))
        }
        `when`(newsRepository.getAllNews()).thenReturn(expected)
    }

    @Test
    fun `should get data model from repository`(){
        val result = useCase.getAllNews()
        verify(newsRepository).getAllNews()
        Assert.assertNotNull(result)
    }

}
package com.nextgen.beritaku.core.domain.usecase

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import com.nextgen.beritaku.core.utils.DataDummy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsInteractorTest {

    private lateinit var useCase: NewsInteractor

    @Mock
    private lateinit var newsRepository: INewsRepository

    @Before
    fun setup() {
        useCase = NewsInteractor(newsRepository)
        val expected: Flow<Resource<List<NewsDataItem>>> = flow {
            emit(Resource.Success(DataDummy.generateListNewsData()))
        }
        Mockito.`when`(newsRepository.getAllNews()).thenReturn(expected)
    }

    @Test
    fun getAllNews() {
        val result = useCase.getAllNews()
        Mockito.verify(newsRepository).getAllNews()
        assertNotNull(result)
    }

    @Test
    fun getAllNewsByCategory() {
    }

    @Test
    fun searchNews() {

    }

    @Test
    fun getFavoriteNews() {
    }

    @Test
    fun isFavoriteNews() {
    }

    @Test
    fun insertFavoriteNews() {
    }

    @Test
    fun deleteNews() {
    }
}
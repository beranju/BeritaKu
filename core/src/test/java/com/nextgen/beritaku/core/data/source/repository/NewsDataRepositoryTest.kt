package com.nextgen.beritaku.core.data.source.repository

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.data.source.local.room.NewsDataDao
import com.nextgen.beritaku.core.data.source.remote.network.ApiService
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import com.nextgen.beritaku.core.utils.DataDummy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsDataRepositoryTest {

    private lateinit var repository: INewsRepository<NewsDataItem>

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var newsDao: NewsDataDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = NewsDataRepository(apiService, newsDao)
    }

    @Test
    fun newsDataRepository_getAllNews_verifyResourceSuccess() = runTest {
        val fakeData = DataDummy.generateListNewsData()
        val expected: Flow<Resource<List<NewsDataItem>>> = flowOf(Resource.Success(fakeData))
        val actual = repository.getAllNews()
        assertEquals(expected, actual)
    }
}
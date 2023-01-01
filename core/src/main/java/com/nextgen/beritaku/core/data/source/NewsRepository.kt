package com.nextgen.beritaku.core.data.source

import com.nextgen.beritaku.core.data.source.local.LocalDataSource
import com.nextgen.beritaku.core.data.source.remote.RemoteDataSource
import com.nextgen.beritaku.core.data.source.remote.network.ApiResponse
import com.nextgen.beritaku.core.data.source.remote.response.ArticlesItem
import com.nextgen.beritaku.core.data.source.remote.response.NewsResponse
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import com.nextgen.beritaku.core.utils.AppExecutors
import com.nextgen.beritaku.core.utils.DataMapper
import dagger.Module
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
): INewsRepository {
    override fun getAllNews(category: String, query: String?): Flow<Resource<List<NewsModel>>> =
        object : NetworkBoundResources<List<NewsModel>, List<ArticlesItem>>(appExecutors){
            override suspend fun saveCallResult(data: List<ArticlesItem>) {
                val newsList = DataMapper.mapResponseToEntity(data)
                localDataSource.insertNews(newsList)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ArticlesItem>>> {
                return remoteDataSource.getAllNews(category, query)
            }

            override fun shouldFetch(dbSource: List<NewsModel>?): Boolean = true

            override fun loadFromDb(): Flow<List<NewsModel>> {
                return localDataSource.getAllNews().map {
                    DataMapper.entityToDomain(it)
                }
            }

        }.asFlow()

    override fun getFavoriteNews(): Flow<List<NewsModel>> {
        return localDataSource.getFavoriteNews().map {
            DataMapper.entityToDomain(it)
        }
    }

    override fun setFavoriteNews(newsModel: NewsModel, state: Boolean) {
        val newsEntity = DataMapper.domainToEntity(newsModel)
        appExecutors.diskIO().execute{localDataSource.setFavoriteNews(newsEntity, state)}
    }
}
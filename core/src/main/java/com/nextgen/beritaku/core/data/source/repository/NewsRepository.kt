package com.nextgen.beritaku.core.data.source.repository

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.data.source.local.room.NewsDao
import com.nextgen.beritaku.core.data.source.remote.network.ApiService
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import com.nextgen.beritaku.core.utils.DataMapper.mapNewsDataResponseToNewsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class NewsRepository(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
) : INewsRepository {


    //    override fun getAllNews(): Flow<Resource<List<NewsModel>>> =
//        flow {
//            emit(Resource.Loading())
//            try {
//                val response = apiService.getAllNews("general", null, null)
//                emit(Resource.Success(DataMapper.mapResponseToModel(response.articles!!)))
//            }catch (e: Exception){
//                emit(Resource.Error(e.localizedMessage!!))
//            }
//        }.flowOn(Dispatchers.Default)
//
//    override fun getAllNewsByCategory(
//        category: String,
//        query: String?,
//    ): Flow<Resource<List<NewsModel>>> =
//        flow {
//            emit(Resource.Loading())
//            try {
//                val response = apiService.getAllNews(category, query, null)
//                emit(Resource.Success(DataMapper.mapResponseToModel(response.articles!!)))
//            }catch (e: Exception){
//                emit(Resource.Error(e.localizedMessage!!))
//            }
//        }.flowOn(Dispatchers.IO)
//
//    override fun getNewsByQuery(query: String): Flow<Resource<List<NewsModel>>> =
//        flow {
//            emit(Resource.Loading())
//            try {
//                val response = apiService.getAllNews("general", query, null)
//                if (response.articles != null){
//                    val data = DataMapper.mapResponseToModel(response.articles)
//                    emit(Resource.Success(data))
//                }else{
//                    emit(Resource.Error("News Empty"))
//                }
//            }catch (e: Exception){
//                emit(Resource.Error(e.localizedMessage!!))
//            }
//        }.flowOn(Dispatchers.IO)
//            // ** fetch latest news
//            .conflate()
//
//    override fun getFavoriteNews(): Flow<Resource<List<NewsModel>>> =
//        flow {
//            emit(Resource.Loading())
//            try {
//                newsDao.getAllNews()
//                    .catch { Resource.Error(it.message.toString(), null) }
//                    .collect{data ->
//                        val dataMapper = DataMapper.entityToDomain(data)
//                        emit(Resource.Success(dataMapper))
//                    }
//            }catch (e: Exception){
//                emit(Resource.Error(e.localizedMessage!!, null))
//            }
//        }
//
//    override suspend fun isFavoriteNews(publishAt: String): Boolean =newsDao.isNewsFavorite(publishAt)
//
//    override suspend fun insertFavoriteNews(news: NewsModel) {
//        DataMapper.domainToEntity(news).let {
//            newsDao.insertFavoriteNews(it)
//        }
//    }
//
//    override suspend fun deleteNews(news: NewsModel) {
//        DataMapper.domainToEntity(news).let {
//            newsDao.deleteNews(it)
//        }
//    }
    override fun getAllNews(): Flow<Resource<List<NewsDataItem>>> =
        flow<Resource<List<NewsDataItem>>> {
            emit(Resource.Loading())
            try {
                val response = apiService.getAllNewsData()
                if (response.status == "success") {
                    if (response.results?.isNotEmpty() == true) {
                        val result = response.results.let { items ->
                            items.map { mapNewsDataResponseToNewsData(it!!) }
                        }
                        emit(Resource.Success(result))
                    } else {
                        emit(Resource.Success(emptyList()))
                    }
                } else {
                    emit(Resource.Error("Something went wrong"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage?.toString() ?: "Something went wrong"))
            }
        }.flowOn(Dispatchers.IO)

    override fun getAllNewsByCategory(
        category: String?,
        query: String?
    ): Flow<Resource<List<NewsDataItem>>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getAllNewsData(category = category, query = query)
                if (response.status == "success") {
                    if (response.results?.isNotEmpty() == true) {
                        val result = response.results.let { items ->
                            items.map { mapNewsDataResponseToNewsData(it!!) }
                        }
                        emit(Resource.Success(result))
                    } else {
                        emit(Resource.Success(emptyList()))
                    }
                } else {
                    emit(Resource.Error("Something went wrong"))
                }
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }

}
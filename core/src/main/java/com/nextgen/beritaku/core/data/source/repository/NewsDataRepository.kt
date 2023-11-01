package com.nextgen.beritaku.core.data.source.repository

import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.data.source.local.room.NewsDataDao
import com.nextgen.beritaku.core.data.source.remote.network.ApiService
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import com.nextgen.beritaku.core.utils.DataMapper
import com.nextgen.beritaku.core.utils.DataMapper.mapNewsDataEntityToNewsDataItem
import com.nextgen.beritaku.core.utils.DataMapper.mapNewsDataItemToNewsDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class NewsDataRepository(
    private val apiService: ApiService,
    private val newsDataDao: NewsDataDao
) : INewsRepository<NewsDataItem> {
    override fun getAllNews(): Flow<Resource<List<NewsDataItem>>> =
        flow<Resource<List<NewsDataItem>>> {
            emit(Resource.Loading())
            try {
                val response = apiService.getAllNewsData()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.results?.isNotEmpty() == true) {
                        val result = body.results.let { items ->
                            items.map { DataMapper.mapNewsDataResponseToNewsData(it!!) }
                        }
                        emit(Resource.Success(result))
                    } else {
                        emit(Resource.Success(emptyList()))
                    }
                } else {
                    emit(Resource.Error("Terjadi Kesalahan"))
                }
            } catch (e: IOException) {
                emit(Resource.Error("Koneksi gagal, periksa internet anda"))
            } catch (e: SocketTimeoutException) {
                emit(Resource.Error("Waktu request habis, coba lagi"))
            } catch (e: HttpException) {
                val errorMessage: String = when (e.code()) {
                    400 -> "Kesalahan request, coba lagi"
                    500 -> "Server sedang bermasalah"
                    else -> "Terjadi Kesalahan"
                }
                emit(Resource.Error(errorMessage))
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
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.results?.isNotEmpty() == true) {
                        val result = body.results.let { items ->
                            items.map { DataMapper.mapNewsDataResponseToNewsData(it!!) }
                        }
                        emit(Resource.Success(result))
                    } else {
                        emit(Resource.Success(emptyList()))
                    }
                } else {
                    emit(Resource.Error("Something went wrong"))
                }
            } catch (e: IOException) {
                emit(Resource.Error("Koneksi gagal, periksa internet anda"))
            } catch (e: SocketTimeoutException) {
                emit(Resource.Error("Waktu request habis, coba lagi"))
            } catch (e: HttpException) {
                val errorMessage: String = when (e.code()) {
                    400 -> "Kesalahan request, coba lagi"
                    500 -> "Server sedang bermasalah"
                    else -> "Terjadi Kesalahan"
                }
                emit(Resource.Error(errorMessage))
            }
        }

    override fun getNewsByQuery(query: String): Flow<Resource<List<NewsDataItem>>> =
        flow {
            emit(Resource.Loading())
            try {
                newsDataDao.getAllNews()
                    .catch { Resource.Error(message = it.message.toString(), data = null) }
                    .collect { data ->
                        val newsData = data.map { mapNewsDataEntityToNewsDataItem(it) }
                        emit(Resource.Success(newsData))
                    }
            } catch (e: IOException) {
                emit(Resource.Error("Koneksi gagal, periksa internet anda"))
            } catch (e: SocketTimeoutException) {
                emit(Resource.Error("Waktu request habis, coba lagi"))
            } catch (e: HttpException) {
                val errorMessage: String = when (e.code()) {
                    400 -> "Kesalahan request, coba lagi"
                    500 -> "Server sedang bermasalah"
                    else -> "Terjadi Kesalahan"
                }
                emit(Resource.Error(errorMessage))
            }
        }

    override fun getFavoriteNews(): Flow<Resource<List<NewsDataItem>>> =
        flow {
            emit(Resource.Loading())
            try {
                newsDataDao.getAllNews()
                    .catch { Resource.Error(it.message.toString(), null) }
                    .collect { data ->
                        val listNews = data.map { mapNewsDataEntityToNewsDataItem(it) }
                        emit(Resource.Success(listNews))
                    }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage!!, null))
            }
        }

    override suspend fun isFavoriteNews(id: String): Boolean = newsDataDao.isNewsFavorite(id)


    override suspend fun deleteNews(news: NewsDataItem) {
        mapNewsDataItemToNewsDataEntity(news).let {
            newsDataDao.deleteNews(it)
        }
    }

    override suspend fun insertFavoriteNews(news: NewsDataItem) {
        mapNewsDataItemToNewsDataEntity(news).let {
            newsDataDao.insertFavoriteNews(it)
        }
    }
}
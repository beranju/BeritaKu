package com.nextgen.beritaku.core.data.source.remote

import android.util.Log
import com.nextgen.beritaku.core.data.source.remote.network.ApiResponse
import com.nextgen.beritaku.core.data.source.remote.network.ApiService
import com.nextgen.beritaku.core.data.source.remote.response.ArticlesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllNews(category: String, query: String): Flow<ApiResponse<List<ArticlesItem>>>{
        return flow {
            try {
                val response = apiService.getAllNews(category, query, 20, 1)
                val dataArray = response.articles
                if (dataArray!!.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                }else{
                    ApiResponse.Empty
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "$e")
            }
        }.flowOn(Dispatchers.IO)
    }
}
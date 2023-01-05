package com.nextgen.beritaku.core.data.source.remote

import android.util.Log
import com.nextgen.beritaku.core.data.source.remote.network.ApiResponse
import com.nextgen.beritaku.core.data.source.remote.network.ApiService
import com.nextgen.beritaku.core.data.source.remote.response.ArticlesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource (private val apiService: ApiService) {
    suspend fun getAllNews(category: String, query: String?, pageSize: Int?): Flow<ApiResponse<List<ArticlesItem>>>{
        return flow {
            try {
                val response = apiService.getAllNews(category, query, pageSize)
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

    suspend fun searchNews(query: String?): Flow<ApiResponse<List<ArticlesItem>>>{
        return flow {
            try {
                val response = apiService.searchNews(query)
                val dataArray = response.articles
                if (dataArray!!.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                }else{
                    ApiResponse.Empty
                }
            }catch (e: Exception){
                ApiResponse.Error(e.toString())
                Log.e("RemoteDataSource", "${e.message}")
            }
        }.flowOn(Dispatchers.Default)
    }
}
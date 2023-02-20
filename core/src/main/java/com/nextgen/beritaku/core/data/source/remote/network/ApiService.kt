package com.nextgen.beritaku.core.data.source.remote.network

import com.nextgen.beritaku.core.BuildConfig
import com.nextgen.beritaku.core.data.source.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val API_KEY = BuildConfig.API_KEY

interface ApiService {
    
    @GET("top-headlines")
    @Headers("Authorization: apiKey $API_KEY", "User-Agent: request")
    suspend fun getAllNews(
        @Query("category") category: String?,
        @Query("q") query: String?,
        @Query("pageSize") pageSize: Int?,
        @Query("page") page: Int? = 1,
        @Query("country") country: String = "id"
    ): NewsResponse


}
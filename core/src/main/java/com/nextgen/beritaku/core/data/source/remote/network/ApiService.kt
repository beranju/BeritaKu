package com.nextgen.beritaku.core.data.source.remote.network

import com.nextgen.beritaku.core.data.source.remote.response.NewsDataResponse
import com.nextgen.beritaku.core.data.source.remote.response.NewsResponse
import com.nextgen.beritaku.core.utils.Constants.NEWS_API_API_KEY
import com.nextgen.beritaku.core.utils.Constants.NEWS_DATA_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    @Headers("Authorization: apiKey $NEWS_API_API_KEY", "User-Agent: request")
    suspend fun getAllNews(
        @Query("category") category: String?,
        @Query("q") query: String?,
        @Query("pageSize") pageSize: Int?,
        @Query("page") page: Int? = 1,
        @Query("country") country: String = "us"
    ): NewsResponse

    /**
     * this method use NEWSDATA as API
     */
    @GET("news")
    suspend fun getAllNewsData(
        @Query("category") category: String? = null,
        @Query("q") query: String? = null,
        @Query("qInTitle") queryInTitle: String? = null,
        @Query("qInMeta") queryInMeta: String? = null,
        @Query("image") image: Int = 1,
        @Query("country") country: String = "id",
        @Query("apiKey") apiKey: String = NEWS_DATA_API_KEY
    ): Response<NewsDataResponse>


}
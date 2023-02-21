package com.nextgen.beritaku.core.data.source.local.room

import androidx.room.*
import com.nextgen.beritaku.core.data.source.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("SELECT EXISTS(SELECT * FROM news WHERE publishedAt = :publishAt)")
    suspend fun isNewsFavorite(publishAt: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteNews(news: NewsEntity)

    @Delete
    suspend fun deleteNews(news: NewsEntity)
}
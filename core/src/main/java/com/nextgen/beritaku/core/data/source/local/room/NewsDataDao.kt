package com.nextgen.beritaku.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nextgen.beritaku.core.data.source.local.entity.NewsDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDataDao {
    @Query("SELECT * FROM newsData")
    fun getAllNews(): Flow<List<NewsDataEntity>>

    @Query("SELECT EXISTS(SELECT * FROM newsData WHERE articleId = :articleId)")
    suspend fun isNewsFavorite(articleId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteNews(news: NewsDataEntity)

    @Delete
    suspend fun deleteNews(news: NewsDataEntity)
}
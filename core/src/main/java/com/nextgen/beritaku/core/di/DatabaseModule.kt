package com.nextgen.beritaku.core.di

import android.content.Context
import androidx.room.Room
import com.nextgen.beritaku.core.data.source.local.room.NewsDao
import com.nextgen.beritaku.core.data.source.local.room.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):NewsDatabase =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "newsdb"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideNewsDao(database: NewsDatabase): NewsDao = database.newsDao()
}
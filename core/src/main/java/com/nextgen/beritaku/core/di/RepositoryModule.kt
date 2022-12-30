package com.nextgen.beritaku.core.di

import com.nextgen.beritaku.core.data.source.NewsRepository
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(newsRepository: NewsRepository): INewsRepository
}
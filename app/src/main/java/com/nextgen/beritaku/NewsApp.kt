package com.nextgen.beritaku

import android.app.Application
import com.nextgen.beritaku.core.di.databaseModule
import com.nextgen.beritaku.core.di.networkModule
import com.nextgen.beritaku.core.di.repositoryModule
import com.nextgen.beritaku.di.useCaseModule
import com.nextgen.beritaku.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NewsApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@NewsApp)
            koin.loadModules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
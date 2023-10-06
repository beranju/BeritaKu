package com.nextgen.beritaku

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.nextgen.beritaku.core.di.authRepositoryModule
import com.nextgen.beritaku.core.di.databaseModule
import com.nextgen.beritaku.core.di.networkModule
import com.nextgen.beritaku.core.di.repositoryModule
import com.nextgen.beritaku.di.useCaseModule
import com.nextgen.beritaku.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * If you have an existing Application class, then extend that class with SplitCompatApplication and...
 * ...override attachBaseContext() method
 */
class NewsApp: SplitCompatApplication() {
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
                    authRepositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}
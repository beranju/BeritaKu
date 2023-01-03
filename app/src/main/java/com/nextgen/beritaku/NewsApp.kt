package com.nextgen.beritaku

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class NewsApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
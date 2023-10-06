package com.nextgen.beritaku.core.utils

import com.nextgen.beritaku.core.BuildConfig

object Constants {
    const val NEWS_DATA = "newData"
    const val NEWS_API = "newApi"

    const val NEWS_API_API_KEY = BuildConfig.API_KEY
    const val NEWS_API_BASE_URL = BuildConfig.BASE_URL
    const val NEWS_DATA_API_KEY = BuildConfig.NEWSDATA_API_KEY
    const val NEWS_DATA_BASE_URL = BuildConfig.NEWSDATA_BASE_URL

    const val NEWS_API_HOST_NAME = "newsapi.org"
    const val NEWS_DATA_HOST_NAME = "newsdata.io"

    const val TOP_CATEGORY = "top"

    const val WORD_PER_MINUTE = 200
}
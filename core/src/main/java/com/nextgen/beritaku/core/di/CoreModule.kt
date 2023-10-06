package com.nextgen.beritaku.core.di

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.nextgen.beritaku.core.BuildConfig
import com.nextgen.beritaku.core.data.source.local.room.NewsDatabase
import com.nextgen.beritaku.core.data.source.remote.network.ApiService
import com.nextgen.beritaku.core.data.source.repository.AuthRepository
import com.nextgen.beritaku.core.data.source.repository.NewsRepository
import com.nextgen.beritaku.core.domain.repository.IAuthRepository
import com.nextgen.beritaku.core.domain.repository.INewsRepository
import com.nextgen.beritaku.core.utils.Constants.NEWS_API_HOST_NAME
import com.nextgen.beritaku.core.utils.Constants.NEWS_DATA_BASE_URL
import com.nextgen.beritaku.core.utils.Constants.NEWS_DATA_HOST_NAME
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<NewsDatabase>().newsDao() }
    single {
        /**
         * add database encryption to room
         * by add openhelperfactory to initialisation of room
         *
         * put android:allowBackup="false" and android:fullBackupContent="false" in manifest.xml
         * this to avoid app backup data so the app not throw an error :
         * net.sqlcipher.database.SQLiteException: file is not a database.
         */
        val passPhrase: ByteArray = SQLiteDatabase.getBytes("beranju".toCharArray())
        val factory = SupportFactory(passPhrase)

        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java,
            "newsdb"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

}

val networkModule = module {
    single {
        /**
         * add certificate pinning to network with okkhttp
         *
         * https://www.ssllabs.com/ssltest
         * this website can use to get SHA256 of web server
         * add all sha that shown in web
         */
        // ** this certificate use fot NEWSAPI
//        val certificatePinner = CertificatePinner.Builder()
//            .add(NEWS_API_HOST_NAME, "sha256/svXYI8MQpjkQ2SAbnIiqZOvk/sdbTlWScBbeJk4Legk=")
//            .add(NEWS_API_HOST_NAME, "sha256/hS5jJ4P+iQUErBkvoWBQOd1T7VOAYlOVegvv1iMzpxA=")
//            .add(NEWS_API_HOST_NAME, "sha256/7xmA6N1F1gp6ikj57Bg4DMG0jfUB+mZsEL4mZO0qbfU=")
//            .add(NEWS_API_HOST_NAME, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
//            .build()

        // ** this certificate used for NEWSDATA
        val certificatePinner = CertificatePinner.Builder()
            .add(NEWS_DATA_HOST_NAME, "sha256/+LPoBcBSYRVb3qALxSD4hBQF3uhTYa1m9BCB/4NBpHM=")
            .add(NEWS_DATA_HOST_NAME, "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8=")
            .add(NEWS_DATA_HOST_NAME, "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA=")
            .add(NEWS_DATA_HOST_NAME, "sha256/VjLZe/p3W/PJnd6lL8JVNBCGQBZynFLdZSTIqcO0SJ8=")
            .build()

//        val authInterceptor = Interceptor { chain ->
//            val request = chain.request()
//            val newRequest = request.newBuilder()
//                .addHeader("X-ACCESS-KEY", "pub_299936f7b858d8d50d61b8d12d4e0fc43a41e")
//                .build()
//            chain.proceed(newRequest)
//        }
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(NEWS_DATA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val authRepositoryModule = module {
    single<IAuthRepository> { AuthRepository(FirebaseAuth.getInstance()) }
}

val repositoryModule = module {
    single<INewsRepository> { NewsRepository(get(), get()) }
}

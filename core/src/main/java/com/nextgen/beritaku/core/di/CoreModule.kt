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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module{
    factory { get<NewsDatabase>().newsDao() }
    single {
        /**
         * add database encryption to room
         * by add openhelperfactory to initialisation of room
         */
        val passPhrase: ByteArray = SQLiteDatabase.getBytes("beritaku".toCharArray())
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
        val hostName = "newsapi.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostName, "sha256/svXYI8MQpjkQ2SAbnIiqZOvk/sdbTlWScBbeJk4Legk=")
            .add(hostName, "sha256/hS5jJ4P+iQUErBkvoWBQOd1T7VOAYlOVegvv1iMzpxA=")
            .add(hostName, "sha256/7xmA6N1F1gp6ikj57Bg4DMG0jfUB+mZsEL4mZO0qbfU=")
            .add(hostName, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val authRepositoryModule = module {
    single<IAuthRepository> { AuthRepository(FirebaseAuth.getInstance())  }
}

val repositoryModule = module {
    single<INewsRepository> { NewsRepository(get(),get())  }
}

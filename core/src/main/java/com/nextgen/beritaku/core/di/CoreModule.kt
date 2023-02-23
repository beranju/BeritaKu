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
        val hostName = "newsapi.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostName, BuildConfig.SHA1)
            .add(hostName, BuildConfig.SHA2)
            .add(hostName, BuildConfig.SHA3)
            .add(hostName, BuildConfig.SHA4)
            .build()

        val interceptor = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }else{
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
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

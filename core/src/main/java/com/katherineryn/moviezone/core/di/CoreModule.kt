package com.katherineryn.moviezone.core.di

import androidx.room.Room
import com.katherineryn.moviezone.core.data.FilmRepository
import com.katherineryn.moviezone.core.data.source.local.LocalDataSource
import com.katherineryn.moviezone.core.data.source.local.room.FilmDatabase
import com.katherineryn.moviezone.core.data.source.remote.RemoteDataSource
import com.katherineryn.moviezone.core.data.source.remote.network.ApiService
import com.katherineryn.moviezone.core.data.source.remote.network.NetworkConst.BASE_URL
import com.katherineryn.moviezone.core.domain.repository.IFilmRepository
import com.katherineryn.moviezone.core.utils.AppExecutors
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

val databaseModule = module {
    factory { get<FilmDatabase>().filmDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("kathy".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            FilmDatabase::class.java, "MovieZone.db" // i don't know how to name the db correctly, but from Dicoding example, db name can use capital char
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(hostname, "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
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
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IFilmRepository> {
        FilmRepository(
            get(),
            get(),
            get()
        )
    }
}
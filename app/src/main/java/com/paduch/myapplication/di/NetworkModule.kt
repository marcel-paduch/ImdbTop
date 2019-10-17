package com.paduch.myapplication.di

import com.google.gson.GsonBuilder
import com.paduch.myapplication.data.remote.service.ImdbService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val API_KEY_FIELD = "api_key"
        private const val API_KEY_VALUE = "11004c5dda64d0bae607c7af2636e983"
    }

    @Provides
    @Singleton
    internal fun provideTopMoviesApi(retrofit: Retrofit): ImdbService {
        return retrofit.create(ImdbService::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor {
                var request = it.request()
                val url =
                    request.url().newBuilder().addQueryParameter(API_KEY_FIELD, API_KEY_VALUE).build()
                request = request.newBuilder().url(url).build()
                it.proceed(request)
            }.build()

        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .create()
                    )
            )
            .client(httpClient)
            .baseUrl(BASE_URL)
            .build()
    }
}
package com.simmons.testbed.di

import com.simmons.testbed.api.SimmonsAPI
import com.simmons.testbed.model.SimmonsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SimmonsModule {
    const val BASE_URL = "http://210.94.223.124:9200/"

    @Singleton
    @Provides
    fun provideSimmonsAPI(): SimmonsAPI {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SimmonsAPI::class.java)
    }

    @Provides
    fun provideSimmonsRepository(simmonsAPI: SimmonsAPI): SimmonsRepository {
        return SimmonsRepository(simmonsAPI)
    }
}

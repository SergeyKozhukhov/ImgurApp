package ru.leisure.imgur.core.network.impl

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(20_000, TimeUnit.MILLISECONDS)
            .writeTimeout(20_000, TimeUnit.MILLISECONDS)
            .build()
    }
}
package ru.leisure.imgur.di

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import ru.leisure.imgur.data.ImgurRepositoryImpl
import ru.leisure.imgur.data.converters.ImageConverter
import ru.leisure.imgur.data.datasources.ImgurDataSource
import ru.leisure.imgur.data.datasources.ImgurDataSourceImpl
import ru.leisure.imgur.domain.ImgurInteractor
import ru.leisure.imgur.domain.ImgurInteractorImpl
import ru.leisure.imgur.domain.ImgurRepository
import java.util.concurrent.TimeUnit

class DiContainer {

    val imgurInteractor: ImgurInteractor by lazy(LazyThreadSafetyMode.NONE) {
        ImgurInteractorImpl(repository = provideImgurRepository())
    }


    private fun provideImgurRepository(): ImgurRepository =
        ImgurRepositoryImpl(
            dataSource = provideImgurDataSource(),
            converter = ImageConverter()
        )

    private fun provideImgurDataSource(): ImgurDataSource =
        ImgurDataSourceImpl(
            okHttpClient = provideOkHttpClient(),
            objectMapper = ObjectMapper()
        )

    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(3000, TimeUnit.MILLISECONDS)
        .writeTimeout(3000, TimeUnit.MILLISECONDS)
        .build()
}
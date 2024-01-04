package ru.leisure.imgur.di

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import ru.leisure.imgur.core.base.api.coroutine.Dispatcher
import ru.leisure.imgur.data.ImgurRepositoryImpl
import ru.leisure.imgur.data.datasources.ImgurDataSource
import ru.leisure.imgur.data.datasources.ImgurDataSourceImpl
import ru.leisure.imgur.domain.ImgurInteractor
import ru.leisure.imgur.domain.ImgurInteractorImpl
import ru.leisure.imgur.domain.ImgurRepository
import java.util.concurrent.TimeUnit

@Module
object AppModule {

    @Provides
    fun provideImgurInteractor(dispatcher: Dispatcher): ImgurInteractor {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(3000, TimeUnit.MILLISECONDS)
            .writeTimeout(3000, TimeUnit.MILLISECONDS)
            .build()

        val imgurDataSource: ImgurDataSource = ImgurDataSourceImpl(
            okHttpClient = okHttpClient,
            objectMapper = ObjectMapper()
        )

        val repository: ImgurRepository = ImgurRepositoryImpl(
            dataSource = imgurDataSource,
            dispatcher = dispatcher
        )

        return ImgurInteractorImpl(repository = repository)
    }
}
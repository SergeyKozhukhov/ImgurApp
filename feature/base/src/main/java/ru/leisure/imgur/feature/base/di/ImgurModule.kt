package ru.leisure.imgur.feature.base.di

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import ru.leisure.imgur.core.coroutines.api.Dispatcher
import ru.leisure.imgur.feature.base.data.ImgurRepositoryImpl
import ru.leisure.imgur.feature.base.data.datasources.ImgurDataSource
import ru.leisure.imgur.feature.base.data.datasources.ImgurDataSourceImpl
import ru.leisure.imgur.feature.base.domain.ImgurInteractor
import ru.leisure.imgur.feature.base.domain.ImgurInteractorImpl
import ru.leisure.imgur.feature.base.domain.ImgurRepository

@Module
object ImgurModule {

    @Provides
    fun provideImgurInteractor(
        okHttpClient: OkHttpClient,
        objectMapper: ObjectMapper,
        dispatcher: Dispatcher
    ): ImgurInteractor {

        val imgurDataSource: ImgurDataSource = ImgurDataSourceImpl(
            okHttpClient = okHttpClient,
            objectMapper = objectMapper
        )

        val repository: ImgurRepository = ImgurRepositoryImpl(
            dataSource = imgurDataSource,
            dispatcher = dispatcher
        )

        return ImgurInteractorImpl(repository = repository)
    }
}
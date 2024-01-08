package ru.leisure.imgur.feature.base.di

import dagger.Module
import dagger.Provides
import ru.leisure.imgur.core.coroutines.api.Dispatcher
import ru.leisure.imgur.core.network.api.NetworkClient
import ru.leisure.imgur.core.parser.api.JsonParser
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
        networkClient: NetworkClient,
        jsonParser: JsonParser,
        dispatcher: Dispatcher
    ): ImgurInteractor {

        val imgurDataSource: ImgurDataSource = ImgurDataSourceImpl(
            networkClient = networkClient,
            jsonParser = jsonParser
        )

        val repository: ImgurRepository = ImgurRepositoryImpl(
            dataSource = imgurDataSource,
            dispatcher = dispatcher
        )

        return ImgurInteractorImpl(repository = repository)
    }
}
package ru.leisure.imgur.feature.base.di

import dagger.Component
import ru.leisure.imgur.core.base.factory.CoreProvidersFactory
import ru.leisure.imgur.core.coroutines.api.CoroutinesProvider
import ru.leisure.imgur.core.network.api.NetworkProvider
import ru.leisure.imgur.core.parser.api.ParserProvider
import ru.leisure.imgur.feature.base.domain.ImgurInteractor

@Component(
    dependencies = [CoroutinesProvider::class, NetworkProvider::class, ParserProvider::class],
    modules = [ImgurModule::class]
)
interface ImgurComponent {

    val imgurInteractor: ImgurInteractor

    companion object {

        fun create(): ImgurComponent = DaggerImgurComponent.builder()
            .coroutinesProvider(CoreProvidersFactory.createCoroutinesProvider())
            .networkProvider(CoreProvidersFactory.createNetworkProvider())
            .parserProvider(CoreProvidersFactory.createParserProvider())
            .build()
    }
}
package ru.leisure.imgur.feature.base.di

import dagger.Component
import ru.leisure.imgur.core.base.api.coroutine.CoroutineProvider
import ru.leisure.imgur.core.base.factory.CoreProvidersFactory
import ru.leisure.imgur.feature.base.domain.ImgurInteractor

@Component(
    dependencies = [CoroutineProvider::class],
    modules = [ImgurModule::class]
)
interface ImgurComponent {

    val imgurInteractor: ImgurInteractor

    companion object {

        fun create(): ImgurComponent = DaggerImgurComponent.builder()
            .coroutineProvider(CoreProvidersFactory.createCoroutineProvider())
            .build()
    }
}
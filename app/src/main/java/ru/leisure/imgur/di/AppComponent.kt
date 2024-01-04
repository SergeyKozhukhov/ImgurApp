package ru.leisure.imgur.di

import dagger.Component
import ru.leisure.imgur.core.base.api.coroutine.CoroutineProvider
import ru.leisure.imgur.domain.ImgurInteractor

@Component(
    dependencies = [CoroutineProvider::class],
    modules = [AppModule::class]
)
interface AppComponent {

    val imgurInteractor: ImgurInteractor
}
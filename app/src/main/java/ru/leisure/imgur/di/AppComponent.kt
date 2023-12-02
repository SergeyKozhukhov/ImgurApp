package ru.leisure.imgur.di

import dagger.Component
import ru.leisure.imgur.domain.ImgurInteractor

@Component(modules = [AppModule::class])
interface AppComponent {

    val imgurInteractor: ImgurInteractor
}
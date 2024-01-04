package ru.leisure.imgur.core.base.factory

import ru.leisure.imgur.core.base.api.coroutine.CoroutineProvider
import ru.leisure.imgur.core.base.impl.coroutine.DaggerCoroutineComponent

object CoreProvidersFactory {

    fun createCoroutineProvider(): CoroutineProvider {
        return DaggerCoroutineComponent.builder().build()
    }
}
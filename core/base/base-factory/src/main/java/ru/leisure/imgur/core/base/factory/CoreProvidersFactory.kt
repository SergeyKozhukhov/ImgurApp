package ru.leisure.imgur.core.base.factory

import ru.leisure.imgur.core.coroutines.api.CoroutinesProvider
import ru.leisure.imgur.core.coroutines.impl.DaggerCoroutinesComponent
import ru.leisure.imgur.core.network.api.NetworkProvider
import ru.leisure.imgur.core.network.impl.DaggerNetworkComponent
import ru.leisure.imgur.core.parser.api.ParserProvider
import ru.leisure.imgur.core.parser.impl.DaggerParserComponent

object CoreProvidersFactory {

    fun createCoroutinesProvider(): CoroutinesProvider {
        return DaggerCoroutinesComponent.builder().build()
    }

    fun createNetworkProvider(): NetworkProvider {
        return DaggerNetworkComponent.builder().build()
    }

    fun createParserProvider(): ParserProvider {
        return DaggerParserComponent.builder().build()
    }
}
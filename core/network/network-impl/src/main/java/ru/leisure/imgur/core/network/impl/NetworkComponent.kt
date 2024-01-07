package ru.leisure.imgur.core.network.impl

import dagger.Component
import ru.leisure.imgur.core.network.api.NetworkProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent : NetworkProvider
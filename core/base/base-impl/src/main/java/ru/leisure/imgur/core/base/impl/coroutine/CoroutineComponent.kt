package ru.leisure.imgur.core.base.impl.coroutine

import dagger.Component
import ru.leisure.imgur.core.base.api.coroutine.CoroutineProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [CoroutineModule::class])
interface CoroutineComponent : CoroutineProvider
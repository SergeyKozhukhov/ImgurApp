package ru.leisure.imgur.core.coroutines.impl

import dagger.Component
import ru.leisure.imgur.core.coroutines.api.CoroutinesProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [CoroutinesModule::class])
interface CoroutinesComponent : CoroutinesProvider
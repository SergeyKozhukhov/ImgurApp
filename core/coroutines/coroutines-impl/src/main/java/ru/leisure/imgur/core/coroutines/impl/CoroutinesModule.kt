package ru.leisure.imgur.core.coroutines.impl

import dagger.Binds
import dagger.Module
import ru.leisure.imgur.core.coroutines.api.Dispatcher

@Module
interface CoroutinesModule {

    @Binds
    fun bindDispatcher(dispatcherImpl: DispatcherImpl): Dispatcher
}
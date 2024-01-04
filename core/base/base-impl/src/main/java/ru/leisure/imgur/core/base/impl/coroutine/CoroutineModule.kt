package ru.leisure.imgur.core.base.impl.coroutine

import dagger.Binds
import dagger.Module
import ru.leisure.imgur.core.base.api.coroutine.Dispatcher

@Module
interface CoroutineModule {

    @Binds
    fun bindDispatcher(dispatcherImpl: DispatcherImpl): Dispatcher
}
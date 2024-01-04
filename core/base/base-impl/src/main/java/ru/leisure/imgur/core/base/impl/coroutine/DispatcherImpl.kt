package ru.leisure.imgur.core.base.impl.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.leisure.imgur.core.base.api.coroutine.Dispatcher
import javax.inject.Inject

class DispatcherImpl @Inject constructor() : Dispatcher {
    override val io: CoroutineDispatcher = Dispatchers.IO
}
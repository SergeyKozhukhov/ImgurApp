package ru.leisure.imgur.core.coroutines.impl

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.leisure.imgur.core.coroutines.api.Dispatcher
import javax.inject.Inject

class DispatcherImpl @Inject constructor() : Dispatcher {
    override val io: CoroutineDispatcher = Dispatchers.IO
}
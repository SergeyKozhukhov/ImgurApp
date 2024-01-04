package ru.leisure.imgur.core.base.api.coroutine

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {

    val io: CoroutineDispatcher
}
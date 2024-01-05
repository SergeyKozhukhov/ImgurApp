package ru.leisure.imgur.core.coroutines.api

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {

    val io: CoroutineDispatcher
}
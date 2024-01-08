package ru.leisure.imgur.core.network.api

import okhttp3.Request

interface NetworkClient {

    @Throws(NetworkException::class)
    fun execute(request: Request): String
}
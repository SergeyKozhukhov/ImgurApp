package ru.leisure.imgur.core.network.api

import okhttp3.OkHttpClient

interface NetworkProvider {

    val networkClient: NetworkClient

    val okHttpClient: OkHttpClient
}
package ru.leisure.imgur.core.network.api

import okhttp3.OkHttpClient

interface NetworkProvider {

    val okHttpClient: OkHttpClient
}
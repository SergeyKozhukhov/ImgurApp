package ru.leisure.imgur.core.network.impl

import okhttp3.OkHttpClient
import okhttp3.Request
import ru.leisure.imgur.core.network.api.NetworkClient
import ru.leisure.imgur.core.network.api.NetworkException
import java.io.IOException

class NetworkClientImpl(
    private val okHttpClient: OkHttpClient
) : NetworkClient {

    override fun execute(request: Request): String {
        try {
            okHttpClient.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    return response.body?.string()
                        ?: throw IllegalStateException("Response body is null").asNetwork()
                } else {
                    throw IllegalStateException("Response is not successful").asNetwork()
                }
            }
        } catch (e: IOException) {
            throw NetworkException(e)
        } catch (e: IllegalStateException) {
            throw NetworkException(e)
        }
    }

    private fun Exception.asNetwork() = NetworkException(this)
}
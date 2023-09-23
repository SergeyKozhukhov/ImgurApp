package ru.leisure.imgur.data.datasources

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.leisure.imgur.BuildConfig
import ru.leisure.imgur.data.models.BasicEntity
import ru.leisure.imgur.data.models.ImageEntity
import ru.leisure.imgur.data.models.ImgurResponseException

class ImgurDataSourceImpl(
    private val okHttpClient: OkHttpClient,
    private val objectMapper: ObjectMapper
) : ImgurDataSource {

    private val httpUrl = DEFAULT_MEMES_URL.toHttpUrl()
    private val request = buildRequest()

    private val typeReference = object : TypeReference<BasicEntity<List<ImageEntity>>>() {}

    override fun getDefaultMemes(): BasicEntity<List<ImageEntity>> {
        val response = okHttpClient.newCall(request).execute()

        if (response.isSuccessful) {
            val result = response.body?.string() ?: throw ImgurResponseException()
            return objectMapper.readValue(result, typeReference)
        }

        throw ImgurResponseException()
    }

    private fun buildRequest() = Request.Builder()
        .url(httpUrl)
        .get()
        .addHeader(AUTH_HEADER_NAME, AUTH_HEADER_VALUE)
        .build()


    private companion object {
        const val DEFAULT_MEMES_URL = "https://api.imgur.com/3/memegen/defaults"
        const val AUTH_HEADER_NAME = "Authorization"
        const val AUTH_HEADER_VALUE = "Client-ID ${BuildConfig.API_KEY}"
    }
}
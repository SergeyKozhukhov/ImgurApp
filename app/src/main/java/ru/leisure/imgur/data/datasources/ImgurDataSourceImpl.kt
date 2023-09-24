package ru.leisure.imgur.data.datasources

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.leisure.imgur.BuildConfig
import ru.leisure.imgur.data.models.BasicEntity
import ru.leisure.imgur.data.models.GalleryAlbumEntity
import ru.leisure.imgur.data.models.ImageEntity
import ru.leisure.imgur.data.models.ImgurResponseException

class ImgurDataSourceImpl(
    private val okHttpClient: OkHttpClient,
    private val objectMapper: ObjectMapper
) : ImgurDataSource {

    private val defaultMemesRequest = buildRequest(url = DEFAULT_MEMES_URL.toHttpUrl())
    private val galleryRequest = buildRequest(url = GALLERY_URL.toHttpUrl())

    private val defaultMemesTypeReference =
        object : TypeReference<BasicEntity<List<ImageEntity>>>() {}
    private val galleryTypeReference =
        object : TypeReference<BasicEntity<List<GalleryAlbumEntity>>>() {}

    override fun getDefaultMemes(): BasicEntity<List<ImageEntity>> {
        val response = okHttpClient.newCall(defaultMemesRequest).execute()

        if (response.isSuccessful) {
            val result = response.body?.string() ?: throw ImgurResponseException()
            return objectMapper.readValue(result, defaultMemesTypeReference)
        }

        throw ImgurResponseException()
    }

    override fun getGallery(): BasicEntity<List<GalleryAlbumEntity>> {
        val response = okHttpClient.newCall(galleryRequest).execute()

        if (response.isSuccessful) {
            val result = response.body?.string() ?: throw ImgurResponseException()
            return objectMapper.readValue(result, galleryTypeReference)
        }

        throw ImgurResponseException()
    }

    private fun buildRequest(url: HttpUrl) = Request.Builder()
        .url(url)
        .get()
        .addHeader(AUTH_HEADER_NAME, AUTH_HEADER_VALUE)
        .build()


    private companion object {
        const val AUTH_HEADER_NAME = "Authorization"
        const val AUTH_HEADER_VALUE = "Client-ID ${BuildConfig.API_KEY}"

        const val DEFAULT_MEMES_URL = "https://api.imgur.com/3/memegen/defaults"
        const val GALLERY_URL = "https://api.imgur.com/3/gallery/hot"
    }
}
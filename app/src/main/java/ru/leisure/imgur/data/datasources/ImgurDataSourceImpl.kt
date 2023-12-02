package ru.leisure.imgur.data.datasources

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.leisure.imgur.BuildConfig
import ru.leisure.imgur.data.models.BasicEntity
import ru.leisure.imgur.data.models.GalleryAlbumEntity
import ru.leisure.imgur.data.models.GalleryTagsEntity
import ru.leisure.imgur.data.models.ImageEntity
import ru.leisure.imgur.data.models.ImgurResponseException
import java.io.IOException

class ImgurDataSourceImpl(
    private val okHttpClient: OkHttpClient,
    private val objectMapper: ObjectMapper
) : ImgurDataSource {

    private val defaultMemesRequest = buildRequest(url = DEFAULT_MEMES_URL.toHttpUrl())
    private val galleryRequest = buildRequest(url = GALLERY_URL.toHttpUrl())
    private val tagsRequest = buildRequest(url = DEFAULT_GALLERY_TAGS_URL.toHttpUrl())

    private val defaultMemesTypeReference =
        object : TypeReference<BasicEntity<List<ImageEntity>>>() {}
    private val galleryTypeReference =
        object : TypeReference<BasicEntity<List<GalleryAlbumEntity>>>() {}
    private val defaultGalleryTagsTypeReference =
        object : TypeReference<BasicEntity<GalleryTagsEntity>>() {}

    override fun getDefaultMemes(): BasicEntity<List<ImageEntity>> =
        makeRequest(defaultMemesRequest, defaultMemesTypeReference)

    override fun getGallery(): BasicEntity<List<GalleryAlbumEntity>> =
        makeRequest(galleryRequest, galleryTypeReference)

    override fun getDefaultGalleryTags(): BasicEntity<GalleryTagsEntity> =
        makeRequest(tagsRequest, defaultGalleryTagsTypeReference)

    override fun searchGallery(query: String): BasicEntity<List<GalleryAlbumEntity>> {
        val request = buildRequest("$SEARCH_GALLERY_URL$query".toHttpUrl())
        return makeRequest(request, galleryTypeReference)
    }

    @Throws(
        IOException::class,
        JsonProcessingException::class,
        JsonMappingException::class,
        ImgurResponseException::class
    )
    private fun <T> makeRequest(request: Request, reference: TypeReference<T>): T {
        val response = okHttpClient.newCall(request).execute()

        if (response.isSuccessful) {
            val result = response.body?.string() ?: throw ImgurResponseException()
            return objectMapper.readValue(result, reference)
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
        const val DEFAULT_GALLERY_TAGS_URL = "https://api.imgur.com/3/tags"
        const val SEARCH_GALLERY_URL = "https://api.imgur.com/3/gallery/search?q="
    }
}
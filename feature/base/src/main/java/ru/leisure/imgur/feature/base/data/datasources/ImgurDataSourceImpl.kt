package ru.leisure.imgur.feature.base.data.datasources

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.leisure.imgur.feature.base.BuildConfig
import ru.leisure.imgur.feature.base.data.models.BasicEntity
import ru.leisure.imgur.feature.base.data.models.CommentEntity
import ru.leisure.imgur.feature.base.data.models.GalleryItemEntity
import ru.leisure.imgur.feature.base.data.models.GalleryTagsEntity
import ru.leisure.imgur.feature.base.data.models.ImgurResponseException
import ru.leisure.imgur.feature.base.data.models.MediaEntity
import java.io.IOException

class ImgurDataSourceImpl(
    private val okHttpClient: OkHttpClient,
    private val objectMapper: ObjectMapper
) : ImgurDataSource {

    private val defaultMemesRequest = buildRequest(url = DEFAULT_MEMES_URL.toHttpUrl())
    private val galleryRequest = buildRequest(url = GALLERY_URL.toHttpUrl())
    private val tagsRequest = buildRequest(url = DEFAULT_GALLERY_TAGS_URL.toHttpUrl())

    private val defaultMemesTypeReference =
        object : TypeReference<BasicEntity<List<MediaEntity>>>() {}
    private val galleryTypeReference =
        object : TypeReference<BasicEntity<List<GalleryItemEntity>>>() {}
    private val defaultGalleryTagsTypeReference =
        object : TypeReference<BasicEntity<GalleryTagsEntity>>() {}
    private val commentsTypeReference =
        object : TypeReference<BasicEntity<List<CommentEntity>>>() {}

    override fun getDefaultMemes() = makeRequest(defaultMemesRequest, defaultMemesTypeReference)

    override fun getGallery() = makeRequest(galleryRequest, galleryTypeReference)

    override fun getDefaultGalleryTags() = makeRequest(tagsRequest, defaultGalleryTagsTypeReference)

    override fun searchGallery(query: String): BasicEntity<List<GalleryItemEntity>> {
        val request = buildRequest("$SEARCH_GALLERY_URL$query".toHttpUrl())
        return makeRequest(request, galleryTypeReference)
    }

    override fun getComments(id: String): BasicEntity<List<CommentEntity>> {
        val request = buildRequest(formCommentsUrl(id).toHttpUrl())
        return makeRequest(request, commentsTypeReference)
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

        const val IMGUR = "https://api.imgur.com"
        const val GALLERY = "$IMGUR/3/gallery"
        const val COMMENTS = "comments"

        const val DEFAULT_MEMES_URL = "$IMGUR/3/memegen/defaults"
        const val GALLERY_URL = "$GALLERY/hot"
        const val DEFAULT_GALLERY_TAGS_URL = "$IMGUR/3/tags"
        const val SEARCH_GALLERY_URL = "$GALLERY/search?q="

        fun formCommentsUrl(id: String) = "$GALLERY/$id/$COMMENTS"
    }
}
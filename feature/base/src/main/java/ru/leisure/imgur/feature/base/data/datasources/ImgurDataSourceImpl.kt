package ru.leisure.imgur.feature.base.data.datasources

import com.fasterxml.jackson.core.type.TypeReference
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import ru.leisure.imgur.core.network.api.NetworkClient
import ru.leisure.imgur.core.network.api.NetworkException
import ru.leisure.imgur.core.parser.api.JsonParser
import ru.leisure.imgur.core.parser.api.ParserException
import ru.leisure.imgur.feature.base.BuildConfig
import ru.leisure.imgur.feature.base.data.models.BasicEntity
import ru.leisure.imgur.feature.base.data.models.CommentEntity
import ru.leisure.imgur.feature.base.data.models.GalleryAlbumEntity
import ru.leisure.imgur.feature.base.data.models.GalleryItemEntity
import ru.leisure.imgur.feature.base.data.models.GalleryTagsEntity
import ru.leisure.imgur.feature.base.data.models.MediaEntity

class ImgurDataSourceImpl(
    private val networkClient: NetworkClient,
    private val jsonParser: JsonParser
) : ImgurDataSource {

    private val defaultMemesRequest = buildRequest(url = DEFAULT_MEMES_URL.toHttpUrl())
    private val tagsRequest = buildRequest(url = DEFAULT_GALLERY_TAGS_URL.toHttpUrl())

    private val defaultMemesTypeReference =
        object : TypeReference<BasicEntity<List<MediaEntity>>>() {}
    private val galleryTypeReference =
        object : TypeReference<BasicEntity<List<GalleryItemEntity>>>() {}
    private val albumTypeReference =
        object : TypeReference<BasicEntity<GalleryAlbumEntity>>() {}
    private val defaultGalleryTagsTypeReference =
        object : TypeReference<BasicEntity<GalleryTagsEntity>>() {}
    private val commentsTypeReference =
        object : TypeReference<BasicEntity<List<CommentEntity>>>() {}

    override fun getDefaultMemes() = makeRequest(defaultMemesRequest, defaultMemesTypeReference)

    override fun getGallery(page: Int): BasicEntity<List<GalleryItemEntity>> {
        val request = buildRequest(formGalleryUrl(page))
        return makeRequest(request, galleryTypeReference)
    }

    override fun getAlbum(id: String): BasicEntity<GalleryAlbumEntity> {
        val request = buildRequest(formAlbumUrl(id))
        return makeRequest(request, albumTypeReference)
    }

    override fun getDefaultGalleryTags() = makeRequest(tagsRequest, defaultGalleryTagsTypeReference)

    override fun searchGallery(query: String): BasicEntity<List<GalleryItemEntity>> {
        val request = buildRequest(formSearchUrl(query))
        return makeRequest(request, galleryTypeReference)
    }

    override fun getComments(id: String): BasicEntity<List<CommentEntity>> {
        val request = buildRequest(formCommentsUrl(id))
        return makeRequest(request, commentsTypeReference)
    }

    @Throws(NetworkException::class, ParserException::class)
    private fun <T> makeRequest(request: Request, reference: TypeReference<T>): T {
        val result = networkClient.execute(request)
        return jsonParser.parse(result, reference)
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
        const val ALBUM_URL = "$GALLERY/album"
        const val DEFAULT_GALLERY_TAGS_URL = "$IMGUR/3/tags"
        const val SEARCH_GALLERY_URL = "$GALLERY/search?q="

        fun formGalleryUrl(page: Int) = "$GALLERY_URL/$page".toHttpUrl()

        fun formSearchUrl(query: String) = "$SEARCH_GALLERY_URL$query".toHttpUrl()

        fun formAlbumUrl(id: String) = "$ALBUM_URL/$id".toHttpUrl()

        fun formCommentsUrl(id: String) = "$GALLERY/$id/$COMMENTS".toHttpUrl()
    }
}
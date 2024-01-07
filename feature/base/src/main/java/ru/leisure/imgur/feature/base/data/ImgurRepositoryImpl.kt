package ru.leisure.imgur.feature.base.data

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import ru.leisure.imgur.core.coroutines.api.Dispatcher
import ru.leisure.imgur.feature.base.data.converters.CommentConverter
import ru.leisure.imgur.feature.base.data.converters.GalleryAlbumConverter
import ru.leisure.imgur.feature.base.data.converters.GalleryItemConverter
import ru.leisure.imgur.feature.base.data.converters.GalleryTagsConverter
import ru.leisure.imgur.feature.base.data.converters.MediaConverter
import ru.leisure.imgur.feature.base.data.datasources.ImgurDataSource
import ru.leisure.imgur.feature.base.domain.ImgurRepository
import ru.leisure.imgur.feature.base.domain.models.Comment
import ru.leisure.imgur.feature.base.domain.models.DataLoadingException
import ru.leisure.imgur.feature.base.domain.models.GalleryAlbum
import ru.leisure.imgur.feature.base.domain.models.GalleryItem

class ImgurRepositoryImpl(
    private val dataSource: ImgurDataSource,
    private val dispatcher: Dispatcher,
    private val mediaConverter: MediaConverter = MediaConverter(),
    private val galleryItemConverter: GalleryItemConverter = GalleryItemConverter(),
    private val galleryAlbumConverter: GalleryAlbumConverter = GalleryAlbumConverter(),
    private val galleryTagsConverter: GalleryTagsConverter = GalleryTagsConverter(),
    private val commentConverter: CommentConverter = CommentConverter()
) : ImgurRepository {

    override suspend fun getDefaultMemes() = withContext(dispatcher.io) {
        try {
            val defaultMemes = dataSource.getDefaultMemes()
            mediaConverter.convert(defaultMemes.data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }

    override suspend fun getGallery(page: Int): List<GalleryItem> = withContext(dispatcher.io) {
        try {
            val gallery = dataSource.getGallery(page)
            galleryItemConverter.convert(gallery.data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }

    override suspend fun getAlbum(id: String): GalleryAlbum = withContext(dispatcher.io) {
        try {
            val gallery = dataSource.getAlbum(id)
            galleryAlbumConverter.convert(gallery.data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }

    override suspend fun getDefaultGalleryTags() = withContext(dispatcher.io) {
        try {
            val tags = dataSource.getDefaultGalleryTags()
            galleryTagsConverter.convert(tags.data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }

    override suspend fun searchGallery(query: String) = withContext(dispatcher.io) {
        try {
            val gallery = dataSource.searchGallery(query)
            galleryItemConverter.convert(gallery.data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }

    override suspend fun getComments(id: String): List<Comment> = withContext(dispatcher.io) {
        try {
            val comments = dataSource.getComments(id)
            commentConverter.convert(comments.data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }
}
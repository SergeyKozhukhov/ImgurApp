package ru.leisure.imgur.feature.base.data

import kotlinx.coroutines.withContext
import ru.leisure.imgur.core.coroutines.api.Dispatcher
import ru.leisure.imgur.core.network.api.NetworkException
import ru.leisure.imgur.core.parser.api.ParserException
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
        execute {
            val defaultMemes = dataSource.getDefaultMemes()
            mediaConverter.convert(defaultMemes.data)
        }
    }

    override suspend fun getGallery(page: Int): List<GalleryItem> = withContext(dispatcher.io) {
        execute {
            val gallery = dataSource.getGallery(page)
            galleryItemConverter.convert(gallery.data)
        }
    }

    override suspend fun getAlbum(id: String): GalleryAlbum = withContext(dispatcher.io) {
        execute {
            val gallery = dataSource.getAlbum(id)
            galleryAlbumConverter.convert(gallery.data)
        }
    }

    override suspend fun getDefaultGalleryTags() = withContext(dispatcher.io) {
        execute {
            val tags = dataSource.getDefaultGalleryTags()
            galleryTagsConverter.convert(tags.data)
        }
    }

    override suspend fun searchGallery(query: String) = withContext(dispatcher.io) {
        execute {
            val gallery = dataSource.searchGallery(query)
            galleryItemConverter.convert(gallery.data)
        }
    }

    override suspend fun getComments(id: String): List<Comment> = withContext(dispatcher.io) {
        execute {
            val comments = dataSource.getComments(id)
            commentConverter.convert(comments.data)
        }
    }

    private fun <T> execute(block: () -> T): T =
        try {
            block.invoke()
        } catch (e: NetworkException) {
            throw DataLoadingException(e)
        } catch (e: ParserException) {
            throw DataLoadingException(e)
        }
}
package ru.leisure.imgur.data

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.leisure.imgur.data.converters.CommentConverter
import ru.leisure.imgur.data.converters.GalleryItemConverter
import ru.leisure.imgur.data.converters.GalleryTagsConverter
import ru.leisure.imgur.data.converters.ImageConverter
import ru.leisure.imgur.data.datasources.ImgurDataSource
import ru.leisure.imgur.domain.ImgurRepository
import ru.leisure.imgur.domain.models.Comment
import ru.leisure.imgur.domain.models.DataLoadingException

class ImgurRepositoryImpl(
    private val dataSource: ImgurDataSource,
    private val imageConverter: ImageConverter = ImageConverter(),
    private val galleryItemConverter: GalleryItemConverter = GalleryItemConverter(),
    private val galleryTagsConverter: GalleryTagsConverter = GalleryTagsConverter(),
    private val commentConverter: CommentConverter = CommentConverter()
) : ImgurRepository {

    override suspend fun getDefaultMemes() = withContext(Dispatchers.IO) {
        try {
            val defaultMemes = dataSource.getDefaultMemes()
            imageConverter.convert(defaultMemes.data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }

    override suspend fun getGallery() = withContext(Dispatchers.IO) {
        try {
            val gallery = dataSource.getGallery()
            galleryItemConverter.convert(gallery.data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }

    override suspend fun getDefaultGalleryTags() = withContext(Dispatchers.IO) {
        try {
            val tags = dataSource.getDefaultGalleryTags()
            galleryTagsConverter.convert(tags.data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }

    override suspend fun searchGallery(query: String) = withContext(Dispatchers.IO) {
        try {
            val gallery = dataSource.searchGallery(query)
            galleryItemConverter.convert(gallery.data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }

    override suspend fun getComments(id: String): List<Comment> = withContext(Dispatchers.IO) {
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
package ru.leisure.imgur.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.leisure.imgur.data.converters.GalleryAlbumConverter
import ru.leisure.imgur.data.converters.ImageConverter
import ru.leisure.imgur.data.datasources.ImgurDataSource
import ru.leisure.imgur.domain.ImgurRepository
import ru.leisure.imgur.domain.models.DataLoadingException

class ImgurRepositoryImpl(
    private val dataSource: ImgurDataSource,
    private val imageConverter: ImageConverter,
    private val galleryConverter: GalleryAlbumConverter
) : ImgurRepository {

    override suspend fun getDefaultMemes() = withContext(Dispatchers.IO) {
        try {
            val defaultMemes = dataSource.getDefaultMemes()
            imageConverter.convert(defaultMemes.data)
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }

    override suspend fun getGallery() = withContext(Dispatchers.IO) {
        try {
            val gallery = dataSource.getGallery()
            galleryConverter.convert(gallery.data)
        } catch (e: Exception) {
            throw DataLoadingException(e)
        }
    }
}
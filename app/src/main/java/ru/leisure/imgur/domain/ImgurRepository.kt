package ru.leisure.imgur.domain

import ru.leisure.imgur.domain.models.DataLoadingException
import ru.leisure.imgur.domain.models.GalleryAlbum
import ru.leisure.imgur.domain.models.Image

interface ImgurRepository {

    @Throws(DataLoadingException::class)
    suspend fun getDefaultMemes(): List<Image>

    @Throws(DataLoadingException::class)
    suspend fun getGallery(): List<GalleryAlbum>
}
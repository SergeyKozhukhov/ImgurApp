package ru.leisure.imgur.domain

import kotlinx.coroutines.CancellationException
import ru.leisure.imgur.domain.models.DataLoadingException
import ru.leisure.imgur.domain.models.GalleryAlbum
import ru.leisure.imgur.domain.models.GalleryTags
import ru.leisure.imgur.domain.models.Image

interface ImgurInteractor {

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun getDefaultMemes(): List<Image>

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun getGallery(): List<GalleryAlbum>

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun getDefaultGalleryTags(): GalleryTags

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun searchGallery(query: String): List<GalleryAlbum>
}
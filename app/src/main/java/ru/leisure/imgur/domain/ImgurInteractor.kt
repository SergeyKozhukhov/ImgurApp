package ru.leisure.imgur.domain

import kotlinx.coroutines.CancellationException
import ru.leisure.imgur.domain.models.Comment
import ru.leisure.imgur.domain.models.DataLoadingException
import ru.leisure.imgur.domain.models.GalleryItem
import ru.leisure.imgur.domain.models.GalleryTags
import ru.leisure.imgur.domain.models.Image

interface ImgurInteractor {

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun getDefaultMemes(): List<Image>

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun getGallery(): List<GalleryItem>

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun getDefaultGalleryTags(): GalleryTags

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun searchGallery(query: String): List<GalleryItem>

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun getComments(id: String): List<Comment>
}
package ru.leisure.imgur.feature.base.domain

import kotlinx.coroutines.CancellationException
import ru.leisure.imgur.feature.base.domain.models.Comment
import ru.leisure.imgur.feature.base.domain.models.DataLoadingException
import ru.leisure.imgur.feature.base.domain.models.GalleryItem
import ru.leisure.imgur.feature.base.domain.models.GalleryTags
import ru.leisure.imgur.feature.base.domain.models.Media

interface ImgurInteractor {

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun getDefaultMemes(): List<Media>

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun getGallery(page: Int = 1): List<GalleryItem>

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun getDefaultGalleryTags(): GalleryTags

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun searchGallery(query: String): List<GalleryItem>

    @Throws(DataLoadingException::class, CancellationException::class)
    suspend fun getComments(id: String): List<Comment>
}
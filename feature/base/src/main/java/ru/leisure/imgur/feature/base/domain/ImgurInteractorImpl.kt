package ru.leisure.imgur.feature.base.domain

import ru.leisure.imgur.feature.base.domain.models.GalleryItem

class ImgurInteractorImpl(private val repository: ImgurRepository) : ImgurInteractor {

    override suspend fun getDefaultMemes() = repository.getDefaultMemes()

    override suspend fun getGallery(page: Int): List<GalleryItem> = repository.getGallery(page)

    override suspend fun getDefaultGalleryTags() = repository.getDefaultGalleryTags()

    override suspend fun searchGallery(query: String) = repository.searchGallery(query)

    override suspend fun getComments(id: String) = repository.getComments(id)
}
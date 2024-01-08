package ru.leisure.imgur.feature.base.domain

class ImgurInteractorImpl(private val repository: ImgurRepository) : ImgurInteractor {

    override suspend fun getDefaultMemes() = repository.getDefaultMemes()

    override suspend fun getGallery(page: Int) = repository.getGallery(page)

    override suspend fun getGalleryAlbum(id: String) = repository.getGalleryAlbum(id)

    override suspend fun getGalleryMedia(id: String) = repository.getGalleryMedia(id)

    override suspend fun getDefaultGalleryTags() = repository.getDefaultGalleryTags()

    override suspend fun getMediaTag(tag: String) = repository.getMediaTag(tag)

    override suspend fun searchGallery(query: String) = repository.searchGallery(query)

    override suspend fun getComments(id: String) = repository.getComments(id)
}
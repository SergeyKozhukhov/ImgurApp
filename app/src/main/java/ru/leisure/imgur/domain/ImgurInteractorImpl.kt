package ru.leisure.imgur.domain

class ImgurInteractorImpl(private val repository: ImgurRepository) : ImgurInteractor {

    override suspend fun getDefaultMemes() = repository.getDefaultMemes()

    override suspend fun getGallery() = repository.getGallery()

    override suspend fun getDefaultGalleryTags() = repository.getDefaultGalleryTags()
}
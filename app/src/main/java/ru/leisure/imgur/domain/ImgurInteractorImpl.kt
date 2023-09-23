package ru.leisure.imgur.domain

class ImgurInteractorImpl(private val repository: ImgurRepository) : ImgurInteractor {

    override suspend fun getDefaultMemes() = repository.getDefaultMemes()

}
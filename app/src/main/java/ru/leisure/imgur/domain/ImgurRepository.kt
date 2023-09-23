package ru.leisure.imgur.domain

interface ImgurRepository {

    suspend fun getDefaultMemes(): List<Image>
}
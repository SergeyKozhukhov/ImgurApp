package ru.leisure.imgur.domain.models


data class Topic(
    val id: Int,
    val name: String,
    val description: String,
    val topPost: GalleryItem
)
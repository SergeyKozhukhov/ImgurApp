package ru.leisure.imgur.feature.base.domain.models


data class Topic(
    val id: Int,
    val name: String,
    val description: String,
    val topPost: GalleryItem
)
package ru.leisure.imgur.feature.base.domain.models

data class MediaTag(
    val name: String,
    val followers: String,
    val totalItems: String,
    val description: String,
    val items: List<GalleryItem>
)
package ru.leisure.imgur.feature.base.domain.models

data class GalleryMedia(
    val media: Media,
    val commentCount: Int,
    val score: Int
) : GalleryItem() {
    override val id get() = media.id
}
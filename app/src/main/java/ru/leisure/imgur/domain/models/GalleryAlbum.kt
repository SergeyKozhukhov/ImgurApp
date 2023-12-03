package ru.leisure.imgur.domain.models

data class GalleryAlbum(
    override val id: String,
    val title: String,
    val link: String,
    val score: Int,
    val commentCount: Int,
    val imagesCount: Int,
    val images: List<Image>
) : GalleryItem()
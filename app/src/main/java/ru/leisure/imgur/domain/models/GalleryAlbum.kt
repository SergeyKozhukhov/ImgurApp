package ru.leisure.imgur.domain.models

import java.net.URI

data class GalleryAlbum(
    override val id: String,
    val title: String,
    val link: URI?,
    val score: Int,
    val commentCount: Int,
    val imagesCount: Int,
    val images: List<Image>
) : GalleryItem()
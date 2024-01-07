package ru.leisure.imgur.feature.base.domain.models

import java.net.URI

data class GalleryAlbum(
    override val id: String,
    val title: String,
    val cover: Media,
    val coverId: String,
    val coverWidth: Int,
    val coverHeight: Int,
    val link: URI?,
    val score: Int,
    val commentCount: Int,
    val mediaCount: Int,
    val mediaList: List<Media>
) : GalleryItem()
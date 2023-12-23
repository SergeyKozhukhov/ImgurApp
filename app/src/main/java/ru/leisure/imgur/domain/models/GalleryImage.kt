package ru.leisure.imgur.domain.models

import java.net.URI

data class GalleryImage(
    override val id: String,
    val title: String,
    val type: String,
    val link: URI?,
    val gifv: URI?,
    val mp4: URI?,
    val commentCount: Int,
    val score: Int
) : GalleryItem()
package ru.leisure.imgur.domain.models

data class GalleryImage(
    override val id: String,
    val title: String,
    val type: String,
    val link: String,
    val gifv: String?,
    val mp4: String?,
    val commentCount: Int,
    val score: Int
) : GalleryItem()
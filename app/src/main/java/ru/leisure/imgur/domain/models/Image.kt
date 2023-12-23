package ru.leisure.imgur.domain.models

import java.net.URI

data class Image(
    val id: String,
    val title: String,
    val description: String?,
    val type: String,
    val isAnimated: Boolean,
    val name: String?,
    val section: String?,
    val link: URI?,
    val gifv: URI?,
    val mp4: URI?,
    val hasSound: Boolean
)
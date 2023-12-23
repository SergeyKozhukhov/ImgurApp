package ru.leisure.imgur.domain.models

data class Image(
    val id: String,
    val title: String,
    val description: String?,
    val type: String,
    val isAnimated: Boolean,
    val name: String?,
    val section: String?,
    val link: String,
    val gifv: String?,
    val mp4: String?,
    val hasSound: Boolean
)
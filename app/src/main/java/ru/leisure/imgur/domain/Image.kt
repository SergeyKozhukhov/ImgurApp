package ru.leisure.imgur.domain

data class Image(
    val id: String,
    val title: String,
    val description: String?,
    val name: String?,
    val link: String
)
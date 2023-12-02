package ru.leisure.imgur.domain.models

data class GalleryTags(
    val tags: List<Tag>,
    val featured: String?,
    val topics: List<Topic>
)
package ru.leisure.imgur.feature.base.domain.models

data class GalleryTags(
    val tags: List<Tag>,
    val featured: String?,
    val topics: List<Topic>
)
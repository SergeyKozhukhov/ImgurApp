package ru.leisure.imgur.feature.base.presentation.tags

import ru.leisure.imgur.feature.base.domain.models.GalleryTags
import ru.leisure.imgur.feature.base.domain.models.MediaTag

data class DefaultGalleryTagsUiState(
    val isDefaultTagsLoading: Boolean = false,
    val defaultTags: GalleryTags? = null,
    val defaultTagsError: String? = null,
    val isMediaTagLoading: Boolean = false,
    val mediaTag: MediaTag? = null,
    val mediaTagError: String? = null
)
package ru.leisure.imgur.feature.base.presentation.tags

import androidx.compose.runtime.Immutable
import ru.leisure.imgur.feature.base.domain.models.GalleryTags

@Immutable
sealed interface DefaultGalleryTagsUiState {

    object Idle : DefaultGalleryTagsUiState

    object Loading : DefaultGalleryTagsUiState

    data class Success(val tags: GalleryTags) : DefaultGalleryTagsUiState

    data class Error(val message: String) : DefaultGalleryTagsUiState
}
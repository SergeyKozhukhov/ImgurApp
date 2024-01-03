package ru.leisure.imgur.presentation.gallery

import androidx.compose.runtime.Immutable
import ru.leisure.imgur.domain.models.GalleryItem

@Immutable
sealed interface GalleryUiState {

    object Idle : GalleryUiState

    object Loading : GalleryUiState

    data class Success(val gallery: List<GalleryItem>) : GalleryUiState

    data class Error(val message: String) : GalleryUiState
}
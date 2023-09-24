package ru.leisure.imgur.presentation.gallery

import ru.leisure.imgur.domain.models.GalleryAlbum

sealed interface GalleryUiState {

    object Loading : GalleryUiState
    data class Success(val gallery: List<GalleryAlbum>) : GalleryUiState
    data class Error(val message: String) : GalleryUiState
}
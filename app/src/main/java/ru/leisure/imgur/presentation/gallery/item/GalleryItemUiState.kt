package ru.leisure.imgur.presentation.gallery.item

import androidx.compose.runtime.Immutable
import ru.leisure.imgur.domain.models.Comment

@Immutable
sealed interface GalleryItemUiState {

    object Idle : GalleryItemUiState

    object Loading : GalleryItemUiState

    data class Success(val comments: List<Comment>) : GalleryItemUiState

    data class Error(val message: String) : GalleryItemUiState
}

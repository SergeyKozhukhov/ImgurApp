package ru.leisure.imgur.feature.base.presentation.gallery.item

import androidx.compose.runtime.Immutable
import ru.leisure.imgur.feature.base.domain.models.Comment
import ru.leisure.imgur.feature.base.domain.models.GalleryItem

@Immutable
sealed interface GalleryItemUiState {

    object Idle : GalleryItemUiState

    object Loading : GalleryItemUiState

    data class Success(
        val galleryItem: GalleryItem,
        val comments: List<Comment>
    ) : GalleryItemUiState

    data class Error(val message: String) : GalleryItemUiState
}

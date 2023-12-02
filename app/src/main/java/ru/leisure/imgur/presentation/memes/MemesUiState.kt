package ru.leisure.imgur.presentation.memes

import androidx.compose.runtime.Immutable
import ru.leisure.imgur.domain.models.Image

@Immutable
sealed interface MemesUiState {

    object Loading : MemesUiState

    data class Success(val memes: List<Image>) : MemesUiState

    data class Error(val message: String) : MemesUiState
}
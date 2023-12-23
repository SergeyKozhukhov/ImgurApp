package ru.leisure.imgur.presentation.memes

import androidx.compose.runtime.Immutable
import ru.leisure.imgur.domain.models.Media

@Immutable
sealed interface MemesUiState {

    object Loading : MemesUiState

    data class Success(val memes: List<Media>) : MemesUiState

    data class Error(val message: String) : MemesUiState
}
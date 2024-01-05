package ru.leisure.imgur.feature.base.presentation.memes

import androidx.compose.runtime.Immutable
import ru.leisure.imgur.feature.base.domain.models.Media

@Immutable
sealed interface MemesUiState {

    object Loading : MemesUiState

    data class Success(val memes: List<Media>) : MemesUiState

    data class Error(val message: String) : MemesUiState
}
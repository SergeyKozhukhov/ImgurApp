package ru.leisure.imgur.presentation.memes

import ru.leisure.imgur.domain.Image

sealed interface MemesUiState {

    object Loading : MemesUiState
    data class Success(val memes: List<Image>) : MemesUiState
    data class Error(val message: String) : MemesUiState
}
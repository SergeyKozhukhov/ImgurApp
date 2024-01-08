package ru.leisure.imgur.feature.base.presentation.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.leisure.imgur.feature.base.presentation.components.ErrorUiState
import ru.leisure.imgur.feature.base.presentation.components.LoadingUiState
import ru.leisure.imgur.feature.base.presentation.viewer.GalleryItemsViewer

@Composable
fun GalleryItemScreen(
    id: String,
    galleryViewModel: GalleryViewModel = viewModel(factory = GalleryViewModel.Factory),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        val uiState by galleryViewModel.uiState.collectAsStateWithLifecycle()
        when (val state = uiState) {
            GalleryUiState.Idle, GalleryUiState.Loading -> LoadingUiState()
            is GalleryUiState.Success -> GalleryItemsViewer(id = id, gallery = state.gallery)
            is GalleryUiState.Error -> ErrorUiState(message = state.message)
        }
    }
}
package ru.leisure.imgur.feature.base.presentation.tags

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
    isTopicItem: Boolean,
    viewModel: DefaultGalleryTagsViewModel = viewModel(factory = DefaultGalleryTagsViewModel.Factory),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        if (isTopicItem) {
            if (uiState.isDefaultTagsLoading) LoadingUiState()
            uiState.defaultTags?.let { tags ->
                GalleryItemsViewer(
                    id = id,
                    gallery = tags.topics.map { it.topPost })
            }
            uiState.defaultTagsError?.let { ErrorUiState(message = it) }
        } else {
            if (uiState.isMediaTagLoading) LoadingUiState()
            uiState.mediaTag?.let { tags ->
                GalleryItemsViewer(
                    id = id,
                    gallery = tags.items
                )
            }
            uiState.mediaTagError?.let { ErrorUiState(message = it) }
        }
    }
}
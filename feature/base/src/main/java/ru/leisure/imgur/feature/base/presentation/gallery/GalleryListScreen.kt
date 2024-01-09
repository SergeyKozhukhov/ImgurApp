package ru.leisure.imgur.feature.base.presentation.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.leisure.imgur.feature.base.domain.models.GalleryItem
import ru.leisure.imgur.feature.base.presentation.components.ErrorUiState
import ru.leisure.imgur.feature.base.presentation.components.LoadingUiState
import ru.leisure.imgur.feature.base.presentation.utils.findCentralItemIndex

@Composable
fun GalleryListScreen(
    viewModel: GalleryViewModel = viewModel(factory = GalleryViewModel.Factory),
    onItemClick: (String) -> Unit
) {
    LaunchedEffect(true) {
        viewModel.loadGallery()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        GalleryUiState.Idle, GalleryUiState.Loading -> LoadingUiState()
        is GalleryUiState.Success -> SuccessUiState(
            gallery = (uiState as GalleryUiState.Success).gallery,
            onItemClick = onItemClick,
            onSearchClick = { viewModel.searchGallery(it) })

        is GalleryUiState.Error -> ErrorUiState(message = (uiState as GalleryUiState.Error).message)
    }
}

@Composable
private fun SuccessUiState(
    gallery: List<GalleryItem>,
    onSearchClick: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    Column {
        SearchBar(onSearchClick = onSearchClick, modifier = Modifier.padding(vertical = 6.dp))
        val listState = rememberLazyListState()
        val centralItemIndex = listState.findCentralItemIndex()
        LazyColumn(state = listState, verticalArrangement = Arrangement.spacedBy(24.dp)) {
            itemsIndexed(gallery) { index, galleryItem ->
                StandardGalleryItemThumbnail(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth(),
                    isPlaybackAllowed = index == centralItemIndex,
                    onItemClick = onItemClick,
                    galleryItem = galleryItem,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(onSearchClick: (String) -> Unit, modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(0.7f)
                .padding(4.dp),
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text(text = "Input") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { onSearchClick.invoke(inputText) }) {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                }
            }
        )
    }
}
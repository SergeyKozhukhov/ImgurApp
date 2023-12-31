package ru.leisure.imgur.feature.base.presentation.gallery.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import ru.leisure.imgur.feature.base.presentation.components.ErrorMessage
import ru.leisure.imgur.feature.base.presentation.components.GalleryItemThumbnail
import ru.leisure.imgur.feature.base.presentation.components.ProgressBar
import ru.leisure.imgur.feature.base.presentation.gallery.GalleryUiState
import ru.leisure.imgur.feature.base.presentation.gallery.GalleryViewModel

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
private fun LoadingUiState() {
    ProgressBar(modifier = Modifier.fillMaxSize())
}

@Composable
private fun SuccessUiState(
    gallery: List<GalleryItem>,
    onSearchClick: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    Column {
        SearchBar(onSearchClick = onSearchClick)
        LazyColumn {
            items(gallery) { galleryItem ->
                GalleryItemThumbnail(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
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
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier.padding(4.dp),
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text(text = "Input") }
        )
        Button(
            modifier = Modifier.padding(4.dp),
            onClick = { onSearchClick.invoke(inputText) }) {
            Text(text = "Search")
        }
    }
}

@Composable
private fun ErrorUiState(message: String) {
    ErrorMessage(message = message, modifier = Modifier.fillMaxSize())
}
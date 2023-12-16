package ru.leisure.imgur.presentation.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.leisure.imgur.R
import ru.leisure.imgur.domain.models.GalleryAlbum
import ru.leisure.imgur.domain.models.GalleryImage
import ru.leisure.imgur.domain.models.GalleryItem
import ru.leisure.imgur.presentation.components.ErrorMessage
import ru.leisure.imgur.presentation.components.ProgressBar

@Composable
fun GalleryItemScreen(
    id: String,
    viewModel: GalleryViewModel = viewModel(factory = GalleryViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        GalleryUiState.Idle, GalleryUiState.Loading -> LoadingUiState()
        is GalleryUiState.Success -> SuccessUiState(id, (uiState as GalleryUiState.Success).gallery)
        is GalleryUiState.Error -> ErrorUiState(message = (uiState as GalleryUiState.Error).message)
    }
}


@Composable
private fun LoadingUiState() {
    ProgressBar(modifier = Modifier.fillMaxSize())
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SuccessUiState(
    id: String,
    gallery: List<GalleryItem>,
) {
    gallery.indexOfFirst { it.id == id }.takeIf { it >= 0 }?.let { initIndex ->
        val pagerState = rememberPagerState(initialPage = initIndex, pageCount = { gallery.size })
        HorizontalPager(state = pagerState) { index ->
            GalleryItemContent(gallery[index], modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun GalleryItemContent(
    item: GalleryItem,
    modifier: Modifier = Modifier
) {
    when (item) {
        is GalleryAlbum -> GalleryAlbumContent(item, modifier)
        is GalleryImage -> GalleryImageContent(item, modifier)
    }
}

@Composable
private fun GalleryAlbumContent(
    album: GalleryAlbum,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(album.images) { image ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.link)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .build(),
                contentDescription = null,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun GalleryImageContent(
    image: GalleryImage,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image.link)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .build(),
        contentDescription = null,
        modifier = modifier
    )
}


@Composable
private fun ErrorUiState(message: String) {
    ErrorMessage(message = message, modifier = Modifier.fillMaxSize())
}
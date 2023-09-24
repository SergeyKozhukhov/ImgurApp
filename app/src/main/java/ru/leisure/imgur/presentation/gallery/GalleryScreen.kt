package ru.leisure.imgur.presentation.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.leisure.imgur.R
import ru.leisure.imgur.domain.models.GalleryAlbum
import ru.leisure.imgur.presentation.components.ErrorMessage
import ru.leisure.imgur.presentation.components.ProgressBar

@Composable
fun GalleryScreen(viewModel: GalleryViewModel = viewModel(factory = GalleryViewModel.Factory)) {
    LaunchedEffect(true) {
        viewModel.loadGallery()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        GalleryUiState.Loading -> LoadingUiState()
        is GalleryUiState.Success -> SuccessUiState(gallery = (uiState as GalleryUiState.Success).gallery)
        is GalleryUiState.Error -> ErrorUiState(message = (uiState as GalleryUiState.Error).message)
    }
}


@Composable
private fun LoadingUiState() {
    ProgressBar(modifier = Modifier.fillMaxSize())
}

@Composable
private fun SuccessUiState(gallery: List<GalleryAlbum>) {
    LazyColumn {
        items(gallery) { galleryAlbum ->
            GalleryAlbumItem(
                galleryAlbum, modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun GalleryAlbumItem(galleryAlbum: GalleryAlbum, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Row {
            galleryAlbum.images.firstOrNull()?.let { image ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.link)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_background)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(86.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(12.dp))),
                )
            }
            Column {
                SmallText(text = galleryAlbum.title)
                SmallText(text = if (galleryAlbum.isAlbum) "It is an album" else "It is not an album")
                SmallText(text = "Popularity score: ${galleryAlbum.score}")
                SmallText(text = "Number of comments: ${galleryAlbum.commentCount}")
                SmallText(text = "The total number of images: ${galleryAlbum.imagesCount}")
            }
        }
    }
}

@Composable
fun SmallText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
private fun ErrorUiState(message: String) {
    ErrorMessage(message = message, modifier = Modifier.fillMaxSize())
}
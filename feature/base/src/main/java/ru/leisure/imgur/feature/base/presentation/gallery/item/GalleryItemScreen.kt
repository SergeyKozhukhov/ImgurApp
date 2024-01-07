package ru.leisure.imgur.feature.base.presentation.gallery.item

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import ru.leisure.imgur.common.video.VideoPlayer
import ru.leisure.imgur.feature.base.R
import ru.leisure.imgur.feature.base.domain.models.GalleryAlbum
import ru.leisure.imgur.feature.base.domain.models.GalleryItem
import ru.leisure.imgur.feature.base.domain.models.GalleryMedia
import ru.leisure.imgur.feature.base.domain.models.Media
import ru.leisure.imgur.feature.base.presentation.components.ErrorMessage
import ru.leisure.imgur.feature.base.presentation.components.ProgressBar
import ru.leisure.imgur.feature.base.presentation.gallery.GalleryUiState
import ru.leisure.imgur.feature.base.presentation.gallery.GalleryViewModel

@Composable
fun GalleryItemScreen(
    id: String,
    galleryViewModel: GalleryViewModel = viewModel(factory = GalleryViewModel.Factory),
) {
    val uiState by galleryViewModel.uiState.collectAsStateWithLifecycle()
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
    modifier: Modifier = Modifier,
    viewModel: GalleryItemViewModel = viewModel(
        key = item.id,
        factory = GalleryItemViewModel.Factory
    )
) {
    LaunchedEffect(Unit) {
        viewModel.loadComments(item.id)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (item) {
        is GalleryAlbum -> GalleryAlbumContent(item, uiState, modifier)
        is GalleryMedia -> GalleryMediaContent(item, uiState, modifier)
    }
}

@Composable
private fun GalleryAlbumContent(
    galleryAlbum: GalleryAlbum,
    uiState: GalleryItemUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(galleryAlbum.mediaList) { media ->
            MediaContent(media)
        }
        commentItems(uiState = uiState)
    }
}

@Composable
private fun GalleryMediaContent(
    galleryMedia: GalleryMedia,
    uiState: GalleryItemUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        item {
            MediaContent(galleryMedia.media)
        }
        commentItems(uiState)
    }
}

@Composable
fun MediaContent(media: Media, modifier: Modifier = Modifier) {
    when (media) {
        is Media.Image -> {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(media.link.toString())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .build(),
                contentDescription = null,
                modifier = modifier
            )
        }

        is Media.Video -> {
            VideoPlayer(
                url = media.link,
                modifier = modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }

        is Media.Animation -> {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .components { add(if (SDK_INT >= 28) ImageDecoderDecoder.Factory() else GifDecoder.Factory()) }
                .build()
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(media.link.toString())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_background)
                        .build(), imageLoader
                ), contentDescription = null, modifier = modifier
            )
            Text("Animation", modifier = modifier)
        }

        is Media.Unknown -> {
            Text("Unknown", modifier = modifier)
        }
    }
}


@Composable
private fun ErrorUiState(message: String) {
    ErrorMessage(message = message, modifier = Modifier.fillMaxSize())
}
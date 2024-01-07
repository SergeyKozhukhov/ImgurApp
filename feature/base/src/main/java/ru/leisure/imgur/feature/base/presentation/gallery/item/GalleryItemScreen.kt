package ru.leisure.imgur.feature.base.presentation.gallery.item

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import ru.leisure.imgur.feature.base.domain.models.Comment
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        val uiState by galleryViewModel.uiState.collectAsStateWithLifecycle()
        when (val state = uiState) {
            GalleryUiState.Idle, GalleryUiState.Loading -> LoadingUiState()
            is GalleryUiState.Success -> SuccessUiState(id = id, gallery = state.gallery)
            is GalleryUiState.Error -> ErrorUiState(message = state.message)
        }
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
        viewModel.loadItem(item)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState) {
        GalleryItemUiState.Idle, GalleryItemUiState.Loading -> LoadingUiState()
        is GalleryItemUiState.Success -> SuccessItemUiState(uiState = state, modifier = modifier)
        is GalleryItemUiState.Error -> ErrorUiState(message = state.message)
    }
}

@Composable
private fun SuccessItemUiState(
    uiState: GalleryItemUiState.Success, modifier: Modifier = Modifier
) {
    when (val item = uiState.galleryItem) {
        is GalleryAlbum -> GalleryAlbumContent(item, uiState.comments, modifier)
        is GalleryMedia -> GalleryMediaContent(item, uiState.comments, modifier)
    }
}


@Composable
private fun GalleryAlbumContent(
    galleryAlbum: GalleryAlbum,
    comments: List<Comment>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(galleryAlbum.mediaList) { media ->
            MediaContent(
                media = media, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item { ViewStructure(galleryAlbum.views) }
        commentItems(comments)
    }
}

@Composable
private fun GalleryMediaContent(
    galleryMedia: GalleryMedia,
    comments: List<Comment>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        item {
            MediaContent(
                galleryMedia.media,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item { ViewStructure(galleryMedia.media.views) }
        commentItems(comments)
    }
}

@Composable
fun MediaContent(media: Media, modifier: Modifier = Modifier) {
    when (media) {
        is Media.Image -> {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(media.link.toString())
                    .placeholder(R.drawable.baseline_panorama_60)
                    .error(R.drawable.ic_launcher_background)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        }

        is Media.Video -> {
            VideoPlayer(
                url = media.link,
                modifier = modifier.height(400.dp)
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
                        .placeholder(R.drawable.baseline_panorama_60)
                        .error(R.drawable.ic_launcher_background)
                        .build(), imageLoader
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
            Text("Animation", modifier = modifier)
        }

        is Media.Unknown -> {
            Text("Unknown", modifier = modifier)
        }
    }
}

@Composable
private fun ViewStructure(views: Int) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(text = "$views views")
    }
}


@Composable
private fun ErrorUiState(message: String) {
    ErrorMessage(message = message, modifier = Modifier.fillMaxSize())
}
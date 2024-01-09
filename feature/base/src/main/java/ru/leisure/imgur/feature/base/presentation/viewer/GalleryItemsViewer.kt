package ru.leisure.imgur.feature.base.presentation.viewer

import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import ru.leisure.imgur.feature.base.presentation.components.ErrorUiState
import ru.leisure.imgur.feature.base.presentation.components.LoadingUiState
import ru.leisure.imgur.feature.base.presentation.utils.findCentralItemIndex

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryItemsViewer(
    id: String,
    gallery: List<GalleryItem>,
) {
    gallery.indexOfFirst { it.id == id }.takeIf { it >= 0 }?.let { initIndex ->
        val pagerState = rememberPagerState(initialPage = initIndex, pageCount = { gallery.size })
        HorizontalPager(state = pagerState) { index ->
            GalleryItemContent(
                item = gallery[index],
                isCurrent = pagerState.currentPage == index,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun GalleryItemContent(
    item: GalleryItem,
    isCurrent: Boolean,
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
        is GalleryItemUiState.Success -> SuccessItemUiState(
            uiState = state,
            isCurrent = isCurrent,
            modifier = modifier
        )

        is GalleryItemUiState.Error -> ErrorUiState(message = state.message)
    }
}

@Composable
private fun SuccessItemUiState(
    uiState: GalleryItemUiState.Success,
    isCurrent: Boolean,
    modifier: Modifier = Modifier
) {
    when (val item = uiState.galleryItem) {
        is GalleryAlbum -> GalleryAlbumContent(item, uiState.comments, isCurrent, modifier)
        is GalleryMedia -> GalleryMediaContent(item, uiState.comments, isCurrent, modifier)
    }
}


@Composable
private fun GalleryAlbumContent(
    galleryAlbum: GalleryAlbum,
    comments: List<Comment>,
    isCurrent: Boolean,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val centralItemIndex = listState.findCentralItemIndex()
    LazyColumn(state = listState, modifier = modifier) {
        itemsIndexed(galleryAlbum.mediaList) { index, media ->
            MediaContent(
                media = media,
                isPlaybackAllowed = isCurrent && index == centralItemIndex,
                modifier = Modifier
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
    isCurrent: Boolean,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        item {
            MediaContent(
                galleryMedia.media,
                isPlaybackAllowed = isCurrent,
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
fun MediaContent(
    media: Media,
    isPlaybackAllowed: Boolean,
    modifier: Modifier = Modifier
) {
    when (media) {
        is Media.Image -> {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(media.link.toString())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        }

        is Media.Video -> {
            VideoPlayer(
                url = media.link,
                modifier = modifier.height(500.dp),
                isPlaybackAllowed = isPlaybackAllowed
            )
        }

        is Media.Animation -> {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .components { add(if (Build.VERSION.SDK_INT >= 28) ImageDecoderDecoder.Factory() else GifDecoder.Factory()) }
                .build()
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(media.link.toString())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
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
    Box(modifier = Modifier.fillMaxWidth().padding(start = 8.dp)) {
        Text(text = "$views views")
    }
}
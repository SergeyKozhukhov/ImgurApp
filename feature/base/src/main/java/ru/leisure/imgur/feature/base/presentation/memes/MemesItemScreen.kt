package ru.leisure.imgur.feature.base.presentation.memes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.leisure.imgur.feature.base.R
import ru.leisure.imgur.feature.base.domain.models.Media
import ru.leisure.imgur.feature.base.presentation.components.ErrorUiState
import ru.leisure.imgur.feature.base.presentation.components.LoadingUiState

@Composable
fun MemesItemScreen(
    imageId: String,
    viewModel: MemesViewModel = viewModel(factory = MemesViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        MemesUiState.Loading -> LoadingUiState()
        is MemesUiState.Success -> SuccessUiState(
            imageId = imageId,
            memes = (uiState as MemesUiState.Success).memes,
        )

        is MemesUiState.Error -> ErrorUiState(message = (uiState as MemesUiState.Error).message)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SuccessUiState(
    imageId: String,
    memes: List<Media>,
) {
    memes.indexOfFirst { it.id == imageId }.takeIf { it >= 0 }?.let { initIndex ->
        val pagerState = rememberPagerState(initialPage = initIndex, pageCount = { memes.size })
        HorizontalPager(state = pagerState) { index ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                MemeItem(
                    media = memes[index],
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(400.dp)
                        .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
                )
            }
        }
    }
}

@Composable
private fun MemeItem(
    media: Media, modifier: Modifier = Modifier
) {
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

        is Media.Animation -> {
            Text("Animation")
        }

        is Media.Video -> {
            Text("Video")
        }

        is Media.Unknown -> {
            Text("Unknown")
        }
    }
}


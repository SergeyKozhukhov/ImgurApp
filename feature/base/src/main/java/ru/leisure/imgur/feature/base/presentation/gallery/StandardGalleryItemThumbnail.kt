package ru.leisure.imgur.feature.base.presentation.gallery

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import ru.leisure.imgur.feature.base.presentation.components.SmallText

@Composable
fun StandardGalleryItemThumbnail(
    galleryItem: GalleryItem,
    isPlaybackAllowed: Boolean,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (galleryItem) {
        is GalleryAlbum -> GalleryAlbumThumbnail(
            galleryAlbum = galleryItem,
            isPlaybackAllowed = isPlaybackAllowed,
            onItemClick = onItemClick,
            modifier = modifier
        )

        is GalleryMedia -> GalleryMediaThumbnail(
            galleryMedia = galleryItem,
            isPlaybackAllowed = isPlaybackAllowed,
            onItemClick = onItemClick,
            modifier = modifier
        )
    }
}

@Composable
private fun GalleryAlbumThumbnail(
    galleryAlbum: GalleryAlbum,
    isPlaybackAllowed: Boolean,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ThumbnailCard(
        media = galleryAlbum.cover,
        title = galleryAlbum.title,
        isAlbum = true,
        score = galleryAlbum.score,
        mediaCount = galleryAlbum.mediaCount,
        isPlaybackAllowed = isPlaybackAllowed,
        onClick = { onItemClick.invoke(galleryAlbum.id) },
        modifier = modifier
    )
}

@Composable
private fun GalleryMediaThumbnail(
    galleryMedia: GalleryMedia,
    isPlaybackAllowed: Boolean,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ThumbnailCard(
        media = galleryMedia.media,
        title = galleryMedia.media.title,
        isAlbum = false,
        score = galleryMedia.score,
        mediaCount = 1,
        isPlaybackAllowed = isPlaybackAllowed,
        onClick = { onItemClick.invoke(galleryMedia.id) },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThumbnailCard(
    media: Media,
    title: String,
    isAlbum: Boolean,
    score: Int,
    mediaCount: Int,
    isPlaybackAllowed: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            onClick = onClick,
            border = BorderStroke(1.dp, Color.DarkGray),
            modifier = Modifier.height(180.dp)
        ) {
            Box {
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
                            modifier = Modifier.fillMaxSize()
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
                            modifier = Modifier.fillMaxSize()
                        )

                        Text(
                            modifier = Modifier
                                .padding(top = 20.dp, start = 16.dp)
                                .padding(4.dp),
                            text = "gif",
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall.copy(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(0f, 0f),
                                    blurRadius = 12f
                                )
                            )
                        )
                    }

                    is Media.Video -> {
                        VideoPlayer(
                            url = media.link,
                            modifier = modifier.height(180.dp),
                            isPlaybackAllowed = isPlaybackAllowed,
                            useController = false,
                            useLooping = true,
                            isSoundEnabled = false
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 20.dp, start = 16.dp)
                                .padding(4.dp),
                            text = "video",
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall.copy(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(0f, 0f),
                                    blurRadius = 12f
                                )
                            )
                        )
                    }

                    is Media.Unknown -> {
                        Text(
                            text = "Unknown",
                            modifier = Modifier
                                .background(Color.Red)
                                .padding(4.dp)
                        )
                    }
                }
                if (isAlbum) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = mediaCount.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall.copy(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(0f, 0f),
                                    blurRadius = 24f
                                )
                            )
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_photo_library_24),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .background(
                                    Brush.radialGradient(
                                        0.0f to Color.Black.copy(alpha = 0.4f),
                                        0.3f to Color.Black.copy(alpha = 0.3f),
                                        0.5f to Color.Black.copy(alpha = 0.1f),
                                        1.0f to Color.Transparent,
                                    ),
                                    shape = CircleShape
                                )
                                .padding(8.dp)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(top = 8.dp)
                .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
        ) {
            SmallText(text = title)
            SmallText(text = "$score score")
        }
    }
}
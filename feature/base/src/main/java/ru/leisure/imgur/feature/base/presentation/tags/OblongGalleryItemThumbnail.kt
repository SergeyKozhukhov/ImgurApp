package ru.leisure.imgur.feature.base.presentation.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.leisure.imgur.feature.base.R
import ru.leisure.imgur.feature.base.domain.models.GalleryAlbum
import ru.leisure.imgur.feature.base.domain.models.GalleryItem
import ru.leisure.imgur.feature.base.domain.models.GalleryMedia
import ru.leisure.imgur.feature.base.domain.models.Media
import ru.leisure.imgur.feature.base.presentation.components.SmallText

@Composable
fun OblongGalleryItemThumbnail(
    galleryItem: GalleryItem,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (galleryItem) {
        is GalleryAlbum -> GalleryAlbumThumbnail(
            galleryAlbum = galleryItem,
            onItemClick = onItemClick,
            modifier = modifier
        )

        is GalleryMedia -> GalleryMediaThumbnail(
            galleryMedia = galleryItem,
            onItemClick = onItemClick,
            modifier = modifier
        )
    }
}

@Composable
private fun GalleryAlbumThumbnail(
    galleryAlbum: GalleryAlbum,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ThumbnailCard(
        media = galleryAlbum.cover,
        title = galleryAlbum.title,
        isAlbum = true,
        score = galleryAlbum.score,
        commentCount = galleryAlbum.commentCount,
        mediaCount = galleryAlbum.mediaCount,
        onClick = { onItemClick.invoke(galleryAlbum.id) },
        modifier = modifier
    )
}

@Composable
private fun GalleryMediaThumbnail(
    galleryMedia: GalleryMedia,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ThumbnailCard(
        media = galleryMedia.media,
        title = galleryMedia.media.title,
        isAlbum = false,
        score = galleryMedia.score,
        commentCount = galleryMedia.commentCount,
        mediaCount = 1,
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
    commentCount: Int,
    mediaCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        onClick = onClick,
        modifier = modifier
    ) {
        Row {
            Box {
                when (media) {
                    is Media.Image -> {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(media.link.toString())
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

                    is Media.Animation -> {
                        Text(
                            text = "Animation",
                            modifier = Modifier
                                .background(Color.Green)
                                .padding(4.dp)
                        )
                    }

                    is Media.Video -> {
                        Text(
                            text = "Video",
                            modifier = Modifier
                                .background(Color.Yellow)
                                .padding(4.dp)
                        )
                        Icon(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.TopEnd),
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = null,
                            tint = Color.White
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
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart)
                        .background(color = Color.LightGray, shape = CircleShape)
                        .padding(2.dp),
                    text = mediaCount.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                SmallText(text = title)
                SmallText(text = if (isAlbum) "Album" else "Image")
                SmallText(text = "Popularity score: $score")
                SmallText(text = "Number of comments: $commentCount")
            }
        }
    }
}
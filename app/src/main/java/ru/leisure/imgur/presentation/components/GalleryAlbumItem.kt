package ru.leisure.imgur.presentation.components

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
import ru.leisure.imgur.R
import ru.leisure.imgur.domain.models.GalleryAlbum
import ru.leisure.imgur.presentation.gallery.SmallText

@Composable
fun GalleryAlbumItem(galleryAlbum: GalleryAlbum, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Row {
            galleryAlbum.images.firstOrNull()?.let { image ->
                Box {
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
                    if (image.mp4 != null) {
                        Icon(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.TopEnd),
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopStart)
                            .background(color = Color.LightGray, shape = CircleShape)
                            .padding(2.dp),
                        text = galleryAlbum.imagesCount.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                SmallText(text = galleryAlbum.title)
                SmallText(text = if (galleryAlbum.isAlbum) "It is an album" else "It is not an album")
                SmallText(text = "Popularity score: ${galleryAlbum.score}")
                SmallText(text = "Number of comments: ${galleryAlbum.commentCount}")
            }
        }
    }
}
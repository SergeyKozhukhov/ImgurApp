package ru.leisure.imgur.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.leisure.imgur.R
import ru.leisure.imgur.domain.models.GalleryAlbum
import ru.leisure.imgur.domain.models.GalleryImage
import ru.leisure.imgur.domain.models.GalleryItem

@Composable
fun GalleryItemContent(galleryItem: GalleryItem, modifier: Modifier = Modifier) {
    when (galleryItem) {
        is GalleryAlbum -> GalleryAlbumItem(galleryAlbum = galleryItem, modifier = modifier)
        is GalleryImage -> GalleryImageItem(galleryImage = galleryItem, modifier = modifier)
    }
}

@Composable
private fun GalleryAlbumItem(galleryAlbum: GalleryAlbum, modifier: Modifier = Modifier) {
    val image = galleryAlbum.images.firstOrNull()
    GalleryItem(
        imageLink = image?.link,
        mp4 = image?.mp4,
        title = galleryAlbum.title,
        isAlbum = true,
        score = galleryAlbum.score,
        commentCount = galleryAlbum.commentCount,
        imagesCount = galleryAlbum.imagesCount,
        modifier = modifier
    )
}

@Composable
private fun GalleryImageItem(galleryImage: GalleryImage, modifier: Modifier = Modifier) {
    GalleryItem(
        imageLink = galleryImage.link,
        mp4 = galleryImage.mp4,
        title = galleryImage.title,
        isAlbum = false,
        score = galleryImage.score,
        commentCount = galleryImage.commentCount,
        imagesCount = 1,
        modifier = modifier
    )
}

@Composable
private fun GalleryItem(
    imageLink: String?,
    mp4: String?,
    title: String,
    isAlbum: Boolean,
    score: Int,
    commentCount: Int,
    imagesCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Row {
            if (imageLink != null) {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageLink)
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
                    if (mp4 != null) {
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
                        text = imagesCount.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
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

@Composable
private fun SmallText(text: String, modifier: Modifier = Modifier) {
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
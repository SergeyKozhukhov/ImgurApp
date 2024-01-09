package ru.leisure.imgur.feature.base.presentation.viewer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import ru.leisure.imgur.feature.base.domain.models.Comment
import ru.leisure.imgur.feature.base.presentation.ui.theme.Purple40

fun LazyListScope.commentItems(comments: List<Comment>) {
    if (comments.isNotEmpty()) {
        item {
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Sharp.Email,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 10.dp),
                    tint = Purple40
                )
                Text(
                    text = "COMMENTS",
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
    items(comments) { comment ->
        CommentItem(comment = comment)
    }
}

@Composable
private fun CommentItem(comment: Comment, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
                .clip(CircleShape)
                .background(Purple40),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = comment.author.first().toString().toUpperCase(Locale.current),
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Text(
                text = comment.author,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = comment.comment,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
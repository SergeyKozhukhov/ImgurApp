package ru.leisure.imgur.feature.base.presentation.viewer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import ru.leisure.imgur.feature.base.domain.models.Comment

fun LazyListScope.commentItems(comments: List<Comment>) {
    items(comments) { comment ->
        CommentItem(comment = comment)
    }
}

@Composable
private fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
                .clip(CircleShape)
                .background(Color.Blue),
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
                .background(Color.LightGray)
        ) {
            Text(text = comment.author)
            Text(text = comment.comment)
        }
    }
}
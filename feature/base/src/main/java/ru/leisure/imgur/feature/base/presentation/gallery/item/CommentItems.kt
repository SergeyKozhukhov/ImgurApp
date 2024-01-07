package ru.leisure.imgur.feature.base.presentation.gallery.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.leisure.imgur.feature.base.domain.models.Comment

fun LazyListScope.commentItems(comments: List<Comment>) {
    items(comments) { comment ->
        CommentItem(comment = comment)
    }
}

@Composable
private fun CommentItem(comment: Comment) {
    Column(modifier = Modifier.padding(4.dp)) {
        Text(text = comment.author)
        Text(text = comment.comment)
        Text(text = comment.children.size.toString())
    }
}
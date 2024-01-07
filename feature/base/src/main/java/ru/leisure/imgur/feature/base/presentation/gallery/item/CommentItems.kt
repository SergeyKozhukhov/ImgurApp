package ru.leisure.imgur.feature.base.presentation.gallery.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.leisure.imgur.feature.base.domain.models.Comment
import ru.leisure.imgur.feature.base.presentation.components.ErrorMessage
import ru.leisure.imgur.feature.base.presentation.components.ProgressBar

fun LazyListScope.commentItems(
    uiState: GalleryItemUiState
) {
    when (uiState) {
        GalleryItemUiState.Idle, GalleryItemUiState.Loading -> loadingItem()
        is GalleryItemUiState.Success -> successItems(comments = uiState.comments)
        is GalleryItemUiState.Error -> errorItem(message = uiState.message)
    }
}

private fun LazyListScope.loadingItem() {
    item {
        ProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
    }
}

private fun LazyListScope.successItems(comments: List<Comment>) {
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

private fun LazyListScope.errorItem(message: String) {
    item {
        ErrorMessage(
            message = message, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
    }
}
package ru.leisure.imgur.feature.base.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainScreenContent(
    val route: String,
    val icon: ImageVector,
    val text: String
) {
    object Gallery : MainScreenContent(
        route = "gallery",
        icon = Icons.Filled.List,
        text = "Gallery"
    )

    object DefaultGalleryTags : MainScreenContent(
        route = "tags",
        icon = Icons.Filled.Menu,
        text = "Tags"
    )

    object Memes : MainScreenContent(
        route = "memes",
        icon = Icons.Filled.Face,
        text = "Memes"
    )

    companion object {
        val bottomDestinations = listOf(Gallery, DefaultGalleryTags, Memes)
    }
}
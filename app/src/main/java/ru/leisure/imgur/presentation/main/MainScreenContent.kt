package ru.leisure.imgur.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainScreenContent(
    val route: String,
    val icon: ImageVector,
    val text: String
) {

    object Memes : MainScreenContent(
        route = "memes",
        icon = Icons.Filled.Face,
        text = "Memes"
    )

    object Other : MainScreenContent(
        route = "other",
        icon = Icons.Filled.Info,
        text = "Other"
    )

    companion object {
        val bottomDestinations = listOf(Memes, Other)
    }
}
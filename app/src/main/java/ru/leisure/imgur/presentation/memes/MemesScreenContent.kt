package ru.leisure.imgur.presentation.memes

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class MemesScreenContent(val route: String) {

    object List : MemesScreenContent(route = "list")

    object Item : MemesScreenContent(route = "item") {
        const val imageIdTypeArg = "image_id_type"
        val routeWithArgs = "${route}/{${imageIdTypeArg}}"
        val arguments = listOf(
            navArgument(imageIdTypeArg) { type = NavType.StringType }
        )
    }
}
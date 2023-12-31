package ru.leisure.imgur.feature.base.presentation.gallery.list

import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.leisure.imgur.feature.base.presentation.main.MainScreenContent

sealed class GalleryScreenContent(val route: String) {

    object List : GalleryScreenContent(route = "${MainScreenContent.Gallery.route}-list")

    object Item : GalleryScreenContent(route = "${MainScreenContent.Gallery.route}-item") {
        const val idArg = "idArg"
        val routeWithArgs = "${route}/{$idArg}"
        val arguments = listOf(
            navArgument(idArg) { type = NavType.StringType }
        )
    }
}
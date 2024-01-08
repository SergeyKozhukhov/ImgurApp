package ru.leisure.imgur.feature.base.presentation.tags

import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.leisure.imgur.feature.base.presentation.main.MainScreenContent

sealed class TagsScreenContent(val route: String) {

    object List : TagsScreenContent(route = "${MainScreenContent.DefaultGalleryTags.route}-list")

    object Item : TagsScreenContent(route = "${MainScreenContent.DefaultGalleryTags.route}-item") {
        const val idArg = "idArg"
        const val isTopicItemArg = "isTopicItemArg"
        val routeWithArgs = "${route}/{$idArg}/{$isTopicItemArg}"
        val arguments = listOf(
            navArgument(idArg) { type = NavType.StringType },
            navArgument(isTopicItemArg) { type = NavType.BoolType }
        )
    }
}
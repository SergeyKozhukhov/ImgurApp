package ru.leisure.imgur.feature.base.presentation.memes

import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.leisure.imgur.feature.base.presentation.main.MainScreenContent

sealed class MemesScreenContent(val route: String) {

    object List : MemesScreenContent(route = "${MainScreenContent.Memes.route}-list")

    object Item : MemesScreenContent(route = "${MainScreenContent.Memes.route}-item") {
        const val idArg = "idArg"
        val routeWithArgs = "${route}/{${idArg}}"
        val arguments = listOf(
            navArgument(idArg) { type = NavType.StringType }
        )
    }
}
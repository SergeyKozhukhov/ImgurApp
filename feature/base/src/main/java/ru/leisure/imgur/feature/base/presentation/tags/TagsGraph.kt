package ru.leisure.imgur.feature.base.presentation.tags

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.leisure.imgur.feature.base.presentation.main.MainScreenContent

fun NavGraphBuilder.tagsGraph(navController: NavController) {
    navigation(
        startDestination = TagsScreenContent.List.route,
        route = MainScreenContent.DefaultGalleryTags.route
    ) {
        composable(route = TagsScreenContent.List.route) { backStackEntry ->
            TagsScreen(
                onTopicClick = { id ->
                    navController.navigate("${TagsScreenContent.Item.route}/$id/${true}")
                },
                onGalleryItemClick = { id ->
                    navController.navigate("${TagsScreenContent.Item.route}/$id/${false}")
                },
                viewModel = tagsViewModel(navController, backStackEntry)
            )
        }
        composable(
            route = TagsScreenContent.Item.routeWithArgs,
            arguments = TagsScreenContent.Item.arguments,
        ) { backStackEntry ->
            val arg = backStackEntry.arguments
            val id = arg?.getString(TagsScreenContent.Item.idArg)
            val isTopicItem = arg?.getBoolean(TagsScreenContent.Item.isTopicItemArg)
            if (id != null && isTopicItem != null) {
                GalleryItemScreen(
                    id = id,
                    isTopicItem = isTopicItem,
                    viewModel = tagsViewModel(navController, backStackEntry)
                )
            }
        }
    }
}

@Composable
private fun tagsViewModel(
    navController: NavController,
    backStackEntry: NavBackStackEntry
): DefaultGalleryTagsViewModel {
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry(MainScreenContent.DefaultGalleryTags.route)
    }
    return viewModel(parentEntry, factory = DefaultGalleryTagsViewModel.Factory)
}
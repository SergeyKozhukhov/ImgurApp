package ru.leisure.imgur.feature.base.presentation.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.leisure.imgur.feature.base.presentation.main.MainScreenContent

fun NavGraphBuilder.galleryGraph(navController: NavController) {
    navigation(
        startDestination = GalleryScreenContent.List.route, route = MainScreenContent.Gallery.route
    ) {
        composable(route = GalleryScreenContent.List.route) { backStackEntry ->
            GalleryListScreen(
                viewModel = galleryViewModel(navController, backStackEntry),
                onItemClick = { id ->
                    navController.navigate("${GalleryScreenContent.Item.route}/$id")
                })
        }
        composable(
            route = GalleryScreenContent.Item.routeWithArgs,
            arguments = GalleryScreenContent.Item.arguments,
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(GalleryScreenContent.Item.idArg)
                ?.let { id ->
                    GalleryItemScreen(
                        galleryViewModel = galleryViewModel(navController, backStackEntry),
                        id = id
                    )
                }
        }
    }
}

@Composable
private fun galleryViewModel(
    navController: NavController,
    backStackEntry: NavBackStackEntry
): GalleryViewModel {
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry(MainScreenContent.Gallery.route)
    }
    return viewModel(parentEntry, factory = GalleryViewModel.Factory)
}
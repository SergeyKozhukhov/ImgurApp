package ru.leisure.imgur.feature.base.presentation.memes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.leisure.imgur.feature.base.presentation.main.MainScreenContent

fun NavGraphBuilder.memesGraph(navController: NavController) {
    navigation(
        startDestination = MemesScreenContent.List.route, route = MainScreenContent.Memes.route
    ) {
        composable(route = MemesScreenContent.List.route) { backStackEntry ->
            MemesListScreen(
                viewModel = memesViewModel(navController, backStackEntry),
                onItemClick = { id ->
                    navController.navigate("${MemesScreenContent.Item.route}/$id")
                })
        }
        composable(
            route = MemesScreenContent.Item.routeWithArgs,
            arguments = MemesScreenContent.Item.arguments,
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(MemesScreenContent.Item.idArg)
                ?.let { id ->
                    MemesItemScreen(
                        viewModel = memesViewModel(navController, backStackEntry),
                        imageId = id
                    )
                }
        }
    }
}

@Composable
private fun memesViewModel(
    navController: NavController,
    backStackEntry: NavBackStackEntry
): MemesViewModel {
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry(MainScreenContent.Memes.route)
    }
    return viewModel(parentEntry, factory = MemesViewModel.Factory)
}
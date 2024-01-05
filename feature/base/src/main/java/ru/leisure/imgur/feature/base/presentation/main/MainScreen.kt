package ru.leisure.imgur.feature.base.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.leisure.imgur.feature.base.presentation.gallery.galleryGraph
import ru.leisure.imgur.feature.base.presentation.memes.memesGraph
import ru.leisure.imgur.feature.base.presentation.tags.TagsScreen
import ru.leisure.imgur.feature.base.presentation.ui.theme.ImgurAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    ImgurAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            val navController: NavHostController = rememberNavController()
            Scaffold(bottomBar = { MainBottomBar(navController) }) { padding ->
                MainScreenContent(navController, modifier = Modifier.padding(padding))
            }
        }
    }
}

@Composable
private fun MainBottomBar(navController: NavHostController) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        MainScreenContent.bottomDestinations.forEach { content ->
            NavigationBarItem(selected = currentDestination?.route == content.route,
                onClick = { navController.navigate(content.route) },
                icon = { Icon(imageVector = content.icon, contentDescription = null) },
                label = { Text(text = content.text) })
        }
    }
}

@Composable
private fun MainScreenContent(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = MainScreenContent.Gallery.route,
        modifier = modifier
    ) {
        galleryGraph(navController = navController)
        composable(MainScreenContent.DefaultGalleryTags.route) { TagsScreen(onItemClick = {}) }
        memesGraph(navController = navController)
    }
}
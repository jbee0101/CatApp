package com.example.catapp.presentation.ui.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * A composable function that displays a bottom navigation bar with two navigation items:
 * "All Cats" and "Favorites".
 *
 * @param navController The NavHostController used to navigate between screens.
 * @param selectedTab The currently selected tab, used to highlight the active item in the navigation bar.
 */
@Composable
fun BottomNavBar(navController: NavHostController, selectedTab: String) {
    NavigationBar {
        val items = listOf("All Cats" to "cat_list", "Favorites" to "favorite_cats")

        items.forEach { (title, route) ->
            NavigationBarItem(
                selected = selectedTab == route,
                icon = { /* Icon will be added later */ },
                label = { Text(title) },
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

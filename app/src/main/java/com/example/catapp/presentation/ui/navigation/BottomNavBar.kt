package com.example.catapp.presentation.ui.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(navController: NavHostController, selectedTab: String) {
    NavigationBar {
        val items = listOf("All Cats" to "cat_list", "Favorites" to "favorite_cats")

        items.forEach { (title, route) ->
            NavigationBarItem(
                selected = selectedTab == route,
                icon = { /* Add Icon if needed */ },
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

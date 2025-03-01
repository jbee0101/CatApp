package com.example.catapp.presentation.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.catapp.presentation.ui.screens.CatListScreen
import com.example.catapp.presentation.ui.screens.FavoriteCatsScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CatNavGraph() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: Screen.CatList.route

    Scaffold (
        bottomBar = {
            if (currentRoute in listOf(Screen.CatList.route, Screen.FavoriteCats.route)) {
                BottomNavBar(navController, currentRoute)
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.CatList.route
        ) {
            composable(Screen.CatList.route) {
                CatListScreen(navController)
            }
            composable(Screen.FavoriteCats.route) {
                FavoriteCatsScreen(navController)
            }
            composable(Screen.CatDetails.route) { backStackEntry ->
                val catId = backStackEntry.arguments?.getString("catId") ?: ""
//                CatDetailScreen(catId) // Need to create this detail screen
            }
        }
    }
}


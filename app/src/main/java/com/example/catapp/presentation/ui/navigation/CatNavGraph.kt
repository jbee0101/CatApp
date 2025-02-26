package com.example.catapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catapp.presentation.ui.screens.CatListScreen

@Composable
fun CatNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.CatList.route) {

        composable(Screen.CatList.route) {
            CatListScreen(navController)
        }
    }
}
package com.example.catapp.presentation.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.catapp.presentation.ui.screens.CatDetailScreen
import com.example.catapp.presentation.ui.screens.CatListScreen
import com.example.catapp.presentation.ui.screens.FavoriteCatsScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/**
 * A composable function that sets up the navigation graph for the CatApp.
 * It includes navigation between the screens for the list of cats, favorite cats, and cat details.
 * It also includes a bottom navigation bar and a top app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatNavGraph() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: Screen.CatList.route

    Scaffold (
        topBar = { TopAppBar(title = { Text("CatApp") }) },
        bottomBar = {
            if (currentRoute in listOf(Screen.CatList.route, Screen.FavoriteCats.route)) {
                BottomNavBar(navController, currentRoute)
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            navController = navController,
            startDestination = Screen.CatList.route
        ) {
            composable(Screen.CatList.route) {
                CatListScreen(navController)
            }
            composable(Screen.FavoriteCats.route) {
                FavoriteCatsScreen(navController)
            }
            composable(
                Screen.CatDetails.route,
                arguments = listOf(navArgument("catId") { type = NavType.StringType },
                navArgument("catImageUrl") { type = NavType.StringType },
                navArgument("catName") { type = NavType.StringType },
                navArgument("catOrigin") { type = NavType.StringType },
                navArgument("catTemperament") { type = NavType.StringType },
                navArgument("catDescription") { type = NavType.StringType },
                navArgument("isFavorite") { type = NavType.BoolType })
            ) { backStackEntry ->
                val catId = backStackEntry.arguments?.getString("catId") ?: ""
                val catImageUrl = backStackEntry.arguments?.getString("catImageUrl") ?: ""
                val catName = backStackEntry.arguments?.getString("catName")?.let {
                    URLDecoder.decode(it, StandardCharsets.UTF_8.name())
                } ?: ""
                val catOrigin = backStackEntry.arguments?.getString("catOrigin")?.let {
                    URLDecoder.decode(it, StandardCharsets.UTF_8.name())
                } ?: ""
                val catTemperament = backStackEntry.arguments?.getString("catTemperament")?.let {
                    URLDecoder.decode(it, StandardCharsets.UTF_8.name())
                } ?: ""
                val catDescription = backStackEntry.arguments?.getString("catDescription")?.let {
                    URLDecoder.decode(it, StandardCharsets.UTF_8.name())
                } ?: ""
                val isFavorite = backStackEntry.arguments?.getBoolean("isFavorite") ?: false

                CatDetailScreen(
                    catId = catId,
                    catImageUrl = catImageUrl,
                    catName = catName,
                    catOrigin = catOrigin,
                    catTemperament = catTemperament,
                    catDescription = catDescription,
                    isFavorite = isFavorite,
                    onBackPress = { navController.popBackStack() }
                )
            }
        }
    }
}


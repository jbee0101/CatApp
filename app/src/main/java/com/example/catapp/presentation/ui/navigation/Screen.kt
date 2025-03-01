package com.example.catapp.presentation.ui.navigation

sealed class Screen(val route: String) {
    object CatList : Screen("cat_list")
    object FavoriteCats : Screen("favorite_cats")
    object CatDetails : Screen("catDetail/{catId}")
}
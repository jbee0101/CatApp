package com.example.catapp.presentation.ui.navigation

sealed class Screen(val route: String) {
    object CatList : Screen("cat_list")
}
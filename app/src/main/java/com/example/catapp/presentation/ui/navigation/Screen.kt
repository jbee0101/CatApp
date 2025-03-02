package com.example.catapp.presentation.ui.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    object CatList : Screen("cat_list")
    object FavoriteCats : Screen("favorite_cats")
    object CatDetails : Screen("cat_detail/{catId}/{catImageUrl}/{catName}/{catOrigin}/{catLifeSpan}/{catTemperament}/{catDescription}/{isFavorite}") {
        fun createRoute(
            catId: String,
            catImageUrl: String,
            catName: String,
            catOrigin: String,
            catLifeSpan: String,
            catTemperament: String,
            catDescription: String,
            isFavorite: Boolean
        ): String {
            return "cat_detail/${catId}/" +
                    "${URLEncoder.encode(catImageUrl, StandardCharsets.UTF_8.name())}/" +
                    "${URLEncoder.encode(catName, StandardCharsets.UTF_8.name())}/" +
                    "${URLEncoder.encode(catOrigin, StandardCharsets.UTF_8.name())}/" +
                    "${URLEncoder.encode(catLifeSpan, StandardCharsets.UTF_8.name())}/" +
                    "${URLEncoder.encode(catTemperament, StandardCharsets.UTF_8.name())}/" +
                    "${URLEncoder.encode(catDescription, StandardCharsets.UTF_8.name())}/" +
                    "$isFavorite"
        }
    }
}
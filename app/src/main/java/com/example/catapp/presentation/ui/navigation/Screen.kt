package com.example.catapp.presentation.ui.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * A sealed class to define the different screen routes in the app.
 * Each screen corresponds to a route that can be used for navigation.
 */
sealed class Screen(val route: String) {
    /**
     * Represents the route for the Cat List screen.
     * The route string is used in the navigation graph to navigate to the Cat List screen.
     */
    object CatList : Screen("cat_list")

    /**
     * Represents the route for the Favorite Cats screen.
     * The route string is used in the navigation graph to navigate to the Favorite Cats screen.
     */
    object FavoriteCats : Screen("favorite_cats")

    /**
     * Represents the route for the Cat Details screen.
     * This screen requires several dynamic arguments passed through the URL, such as catId, catImageUrl, catName, etc.
     */
    object CatDetails : Screen("cat_detail/{catId}/{catImageUrl}/{catName}/{catOrigin}/{catLifeSpan}/{catTemperament}/{catDescription}/{isFavorite}") {

        /**
         * A function to generate a route for the Cat Details screen with the specified parameters.
         * It encodes parameters to ensure special characters are properly handled in URLs.
         *
         * @param catId The ID of the cat.
         * @param catImageUrl The URL of the cat image.
         * @param catName The name of the cat.
         * @param catOrigin The origin of the cat breed.
         * @param catLifeSpan The life span of the cat breed.
         * @param catTemperament The temperament of the cat breed.
         * @param catDescription A description of the cat breed.
         * @param isFavorite A boolean indicating whether the cat is a favorite.
         *
         * @return A string representing the route with the encoded parameters for navigation.
         */
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
package com.example.catapp.data.remote

import com.example.catapp.data.model.CatBreedsResponse
import com.example.catapp.data.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API service interface to interact with the cat endpoints.
 *
 * This service defines the necessary HTTP requests for fetching cat breeds,
 * searching for specific cats by name, and retrieving images of cats.
 */
interface CatApiService {

    /**
     * Retrieves a list of cat breeds.
     *
     * Makes a GET request to the "breeds" endpoint to fetch the information
     * about all the available cat breeds.
     *
     * @return A list of [CatBreedsResponse] objects representing different cat breeds.
     */
    @GET("breeds")
    suspend fun getCatBreeds(): List<CatBreedsResponse>

    /**
     * Retrieves the image associated with a specific cat breed by its ID.
     *
     * Makes a GET request to the "images/{image_id}" endpoint to fetch the image
     * data related to a particular breed using its `image_id`.
     *
     * @param imageId The unique ID of the image to be fetched.
     * @return An [ImageResponse] object containing the URL of the requested image.
     */
    @GET("images/{image_id}")
    suspend fun getCatImage(@Path("image_id") imageId: String): ImageResponse

    /**
     * Searches for cat breeds based on a query string.
     *
     * Makes a GET request to the "breeds/search" endpoint and passes the query
     * parameter to search for cats that match the given name or characteristics.
     *
     * @param query The search query to filter the results.
     * @return A list of [CatBreedsResponse] objects representing the search results.
     */
    @GET("breeds/search")
    suspend fun searchCats(@Query("q") query: String): List<CatBreedsResponse>
}

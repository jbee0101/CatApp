package com.example.catapp.data.remote

import com.example.catapp.data.model.CatBreedsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API service interface to interact with the cat endpoints.
 *
 * This service defines the necessary HTTP requests for fetching cat breeds and
 * searching for specific cats by name.
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
     * Searches for cat breeds based on a query string.
     *
     * Makes a GET request to the "breeds/search" endpoint and passes the query
     * parameter to search for cats that match the given name or characteristics.
     *
     * @param query The search query to filter the results.
     * @return A list of [CatBreedsResponse] objects representing the search results.
     */
    @GET("breeds/search")
    suspend fun searchCats(
        @Query("q") query: String,
        @Query("attach_image") attachImage: Int = 1): List<CatBreedsResponse>
}

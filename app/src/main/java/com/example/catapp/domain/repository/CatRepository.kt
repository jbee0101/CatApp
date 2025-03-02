package com.example.catapp.domain.repository

import com.example.catapp.domain.model.Cat
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the contract for interacting with cat-related data.
 * This repository layer abstracts the data access operations such as fetching,
 * searching, and managing favorite cats, which are provided by a data source.
 */
interface CatRepository {

    /**
     * Retrieves all cats stored in the database, including their favorite status.
     *
     * @return A Flow emitting a list of [Cat] objects representing all the cats.
     */
    suspend fun getAllCats(): Flow<List<Cat>>

    /**
     * Retrieves all the favorite cats from the database.
     *
     * @return A Flow emitting a list of [Cat] objects representing favorite cats.
     */
    suspend fun getFavoriteCats(): Flow<List<Cat>>

    /**
     * Fetches cat data from a remote API and stores it in the local database.
     * This operation involve fetching images.
     */
    suspend fun fetchCats()

    /**
     * Toggles the favorite status of a specific cat in the local database.
     *
     * @param catId The unique identifier of the cat whose favorite status is being toggled.
     * @param isFavorite Boolean flag indicating whether the cat should be marked as a favorite.
     */
    suspend fun toggleFavorite(catId: String, isFavorite: Boolean)

    /**
     * Searches for cats based on a query string, and updates the local database with the search results.
     *
     * @param query A string used to search for specific cat breeds or characteristics.
     */
    suspend fun searchCats(query: String)

    /**
     * Retrieves all the search results for cats from the local database.
     *
     * @return A Flow emitting a list of [Cat] objects based on the most recent search.
     */
    suspend fun getSearchCats(): Flow<List<Cat>>
}

package com.example.catapp.data.repository

import android.util.Log
import com.example.catapp.data.local.CatDao
import com.example.catapp.data.local.FavoriteEntity
import com.example.catapp.data.mapper.toCat
import com.example.catapp.data.mapper.toCatEntity
import com.example.catapp.data.mapper.toSearchCatEntity
import com.example.catapp.data.remote.CatApiService
import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.repository.CatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.math.pow

/**
 * Implementation of the [CatRepository] interface.
 *
 * This repository is responsible for fetching cat-related data from the remote API
 * and local database, as well as managing the favorite status of cats.
 */
class CatRepositoryImpl @Inject constructor(
    private val catApiService: CatApiService,
    private val catDao: CatDao
) : CatRepository {

    /**
     * Fetches all cats from the local database and maps them to the domain model [Cat].
     *
     * @return A [Flow] of a list of [Cat] domain models.
     */
    override suspend fun getAllCats(): Flow<List<Cat>> {
        return catDao.getAllCatsWithFavorites().map { entities ->
            Log.d("CatRepository", "Loading ${entities.size} cats from database")
            entities.map { it.toCat() }
        }
    }

    /**
     * Fetches all favorite cats from the local database and maps them to the domain model [Cat].
     *
     * @return A [Flow] of a list of favorite [Cat] domain models.
     */
    override suspend fun getFavoriteCats(): Flow<List<Cat>> {
        return catDao.getFavoriteCats().map { entities ->
            entities.map { it.toCat() }
        }
    }

    /**
     * Fetches the latest cats data from the API and saves it to the local database.
     * Having the retry mechanise to handle rate limit exceed
     *
     * This method calls the API to get a list of cat breeds, fetches images for each cat,
     * and inserts the resulting data into the local database.
     */
    override suspend fun fetchCats() {
        val retries = 3
        val delayTime = 500L
        var attempt = 0
        var success = false

        while (attempt < retries && !success) {
            try {
                Log.d("CatRepository", "Fetching cats from API")
                val catsResponse = catApiService.getCatBreeds()

                if (catsResponse.isEmpty()) {
                    Log.d("CatRepository", "No cats received from API")
                } else {
                    Log.d("CatRepository", "Received ${catsResponse.size} cats from API")
                }

                val catsWithImages = catsResponse.map { catResponse ->
                    val imageUrl = fetchCatImageUrl(catResponse.referenceImageId)
                    catResponse.toCat().copy(url = imageUrl)
                }

                Log.d("CatRepository", "Saving ${catsWithImages.size} cats to database")

                catDao.insertCats(catsWithImages.map { it.toCatEntity() })

                success = true
            } catch (e: HttpException) {
                if (e.code() == 429) {
                    val waitTime = delayTime * (2.0.pow(attempt.toDouble()).toLong())
                    Log.e("CatRepository", "Rate limit exceeded, retrying after $waitTime ms")
                    delay(waitTime)
                    attempt++
                } else {
                    throw e
                }
            }
        }
    }

    /**
     * Toggles the favorite status of a cat.
     *
     * If the cat is marked as a favorite, it will be added to the favorites table in the database.
     * If not, it will be removed from the favorites table.
     *
     * @param catId The ID of the cat whose favorite status is being toggled.
     * @param isFavorite The new favorite status of the cat.
     */
    override suspend fun toggleFavorite(catId: String, isFavorite: Boolean) {
        if (isFavorite) {
            catDao.addToFavorites(FavoriteEntity(catId))
        } else {
            catDao.removeFromFavorites(catId)
        }
    }

    /**
     * Fetches the image URL for a cat based on the image ID.
     *
     * Calls the API to fetch the image URL using the image ID.
     *
     * @param imageId The image ID associated with the cat.
     * @return The URL of the cat image.
     */
    private suspend fun fetchCatImageUrl(imageId: String?): String {
        if (imageId.isNullOrEmpty()) return ""
        val imageResponse = catApiService.getCatImage(imageId)
        return imageResponse.url
    }

    /**
     * Searches for cats by the given query string and saves the results to the local database.
     *
     * This method calls the API to search for cats by [query], fetches images for each cat,
     * and inserts the results into the local database.
     *
     * @param query The search query.
     */
    override suspend fun searchCats(query: String) {
        Log.d("CatRepository", "Fetching cats from search API for $query ")
        val catsResponse = catApiService.searchCats(query)

        if (catsResponse.isEmpty()) {
            Log.d("CatRepository", "No cats received from API")
        } else {
            Log.d("CatRepository", "Received ${catsResponse.size} cats from API")
        }

        val catsWithImages = catsResponse.map { catResponse ->
            val imageUrl = fetchCatImageUrl(catResponse.referenceImageId)
            catResponse.toCat().copy(url = imageUrl)
        }

        catDao.clearSearchCats()
        catDao.insertSearchCats(catsWithImages.map { it.toSearchCatEntity() })
    }

    /**
     * Fetches the search results from the local database and maps them to the domain model [Cat].
     *
     * @return A [Flow] of a list of search cats mapped to [Cat] domain models.
     */
    override suspend fun getSearchCats(): Flow<List<Cat>> {
        return catDao.getSearchCatsData().map { entities ->
            Log.d("CatRepository", "Loading ${entities.size} search cats from database")
            entities.map { it.toCat() }
        }
    }
}

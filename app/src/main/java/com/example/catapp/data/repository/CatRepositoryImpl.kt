package com.example.catapp.data.repository

import android.util.Log
import com.example.catapp.data.local.CatDao
import com.example.catapp.data.local.CatEntity
import com.example.catapp.data.mapper.toCat
import com.example.catapp.data.mapper.toCatEntity
import com.example.catapp.data.remote.CatApiService
import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.repository.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catApiService: CatApiService,
    private val catDao: CatDao
) : CatRepository {

    override suspend fun getAllCats(): Flow<List<Cat>> {
        return catDao.getAllCats().map { entities ->
            Log.d("CatRepository", "Loading ${entities.size} cats from database")
            entities.map { it.toCat() }
        }
    }

    override suspend fun getFavoriteCats(): Flow<List<Cat>> {
        return catDao.getFavoriteCats().map { entities ->
            entities.map { it.toCat() }
        }
    }

    private suspend fun getFavoritesWithCats(): Map<String,CatEntity> = withContext(Dispatchers.IO) {
        val existingFavorites = catDao.getFavoriteCatsOnce().associateBy { it.id }
        return@withContext existingFavorites
    }

    override suspend fun getCats(page: Int, limit: Int) {
        try {
            Log.d("CatRepository", "Fetching cats from API for page: $page")
            val catsResponse = catApiService.getCatBreeds(limit, page)

            if (catsResponse.isEmpty()) {
                Log.d("CatRepository", "No cats received from API")
            } else {
                Log.d("CatRepository", "Received ${catsResponse.size} cats from API")
            }

            val existingFavorites = getFavoritesWithCats()

            val catsWithImages = catsResponse.map { catResponse ->
                val imageUrl = fetchCatImageUrl(catResponse.reference_image_id) // Fetch image URL
                val isFavorite = existingFavorites[catResponse.id]?.isFavorite ?: false
                catResponse.toCat().copy(url = imageUrl, isFavorite = isFavorite)
            }

            Log.d("CatRepository", "Saving ${catsWithImages.size} cats to database")

            catDao.insertCats(catsWithImages.map { it.toCatEntity() })

//            emit(catsWithImages)

        } catch (e: Exception) {
            Log.e("CatRepository", "Error fetching cats", e)
        }
    }

    override suspend fun toggleFavorite(catId: String, isFavorite: Boolean) {
        catDao.updateFavorite(catId, isFavorite)
    }

    private suspend fun fetchCatImageUrl(imageId: String?): String {
        if (imageId.isNullOrEmpty()) return ""
        val imageResponse = catApiService.getCatImage(imageId)
        return imageResponse.url
    }
}

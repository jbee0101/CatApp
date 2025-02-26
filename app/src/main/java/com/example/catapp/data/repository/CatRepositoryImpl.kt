package com.example.catapp.data.repository

import android.util.Log
import com.example.catapp.data.local.CatDao
import com.example.catapp.data.local.CatEntity
import com.example.catapp.data.remote.CatApiService
import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catApiService: CatApiService,
    private val catDao: CatDao
) : CatRepository {

    override suspend fun getCats(): Flow<List<Cat>> = flow {
        try {
            val response = catApiService.getCatImages() // Need to write logic for handling pagination
            Log.i("CatRep", "========= Response: $response ========")
            val cats = response.map { apiCat ->
                Cat(
                    id = apiCat.id,
                    name = apiCat.breeds.firstOrNull()?.name ?: "Unknown",
                    url = apiCat.url,
                    width = apiCat.width,
                    height = apiCat.height,
                    breedDescription = apiCat.breeds.firstOrNull()?.description ?: "No description",
                    breedLifeSpan = apiCat.breeds.firstOrNull()?.life_span ?: "Unknown",
                    breedOrigin = apiCat.breeds.firstOrNull()?.origin ?: "Unknown",
                    breedTemperament = apiCat.breeds.firstOrNull()?.temperament ?: "Unknown",
                    breedUrl = apiCat.breeds.firstOrNull()?.wikipedia_url ?: ""
                )
            }

            catDao.insertCats(cats.map { cat ->
                CatEntity(
                    id = cat.id,
                    name = cat.name,
                    url = cat.url,
                    imageWidth = cat.width,
                    imageHeight = cat.height,
                    breedName = cat.name,
                    breedDescription = cat.breedDescription,
                    breedLifeSpan = cat.breedLifeSpan,
                    breedOrigin = cat.breedOrigin,
                    breedTemperament = cat.breedTemperament,
                    breedUrl = cat.breedUrl
                )
            })

            emit(cats)
        } catch (e: Exception) {
            val cachedCats = catDao.getAllCats().map { catEntity ->
                Cat(
                    id = catEntity.id,
                    name = catEntity.name,
                    url = catEntity.url,
                    width = catEntity.imageWidth,
                    height = catEntity.imageHeight,
                    breedDescription = catEntity.breedDescription,
                    breedLifeSpan = catEntity.breedLifeSpan,
                    breedOrigin = catEntity.breedOrigin,
                    breedTemperament = catEntity.breedTemperament,
                    breedUrl = catEntity.breedUrl
                )
            }
            emit(cachedCats)
        }
    }
}

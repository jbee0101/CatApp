package com.example.catapp.domain.repository

import com.example.catapp.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {

    suspend fun getAllCats(): Flow<List<Cat>>
    suspend fun getFavoriteCats(): Flow<List<Cat>>
    suspend fun fetchCats()
    suspend fun toggleFavorite(catId: String, isFavorite: Boolean)
    suspend fun searchCats(query: String)
    suspend fun getSearchCats(): Flow<List<Cat>>
}
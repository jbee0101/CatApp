package com.example.catapp.domain.repository

import com.example.catapp.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    suspend fun getCats(): Flow<List<Cat>>
}
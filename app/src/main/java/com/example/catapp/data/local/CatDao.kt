package com.example.catapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCats(cats: List<CatEntity>)

    @Query("SELECT * FROM cats")
    fun getAllCats(): Flow<List<CatEntity>>

    @Query("SELECT * FROM cats WHERE isFavorite = 1")
    fun getFavoriteCats(): Flow<List<CatEntity>>

    @Query("SELECT * FROM cats WHERE isFavorite = 1")
    fun getFavoriteCatsOnce(): List<CatEntity>

    @Query("UPDATE cats SET isFavorite = :isFavorite WHERE id = :catId")
    suspend fun updateFavorite(catId: String, isFavorite: Boolean)
}

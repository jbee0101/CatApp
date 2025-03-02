package com.example.catapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.catapp.data.model.CatWithFavorite
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Transaction
    @Query("SELECT * FROM cats")
    fun getAllCatsWithFavorites(): Flow<List<CatWithFavorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCats(cats: List<CatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE catId = :catId")
    suspend fun removeFromFavorites(catId: String)

    @Transaction
    @Query("SELECT * FROM cats INNER JOIN favorites ON cats.id = favorites.catId")
    fun getFavoriteCats(): Flow<List<CatWithFavorite>>

    @Transaction
    @Query("SELECT * FROM cats INNER JOIN favorites ON cats.id = favorites.catId")
    fun getFavoriteCatsOnce(): List<CatWithFavorite>

    @Query("SELECT * FROM cats")
    fun getAllCats(): Flow<List<CatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchCats(searchCats: List<SearchCatEntity>)

    @Query("DELETE FROM search")
    suspend fun clearSearchCats()

    @Transaction
    @Query("""
        SELECT 
            s.id, 
            s.name, 
            s.breedDescription, 
            s.url,
            s.breedName,
            s.breedDescription,
            s.breedLifeSpan,
            s.breedOrigin,
            s.breedTemperament,
            s.breedUrl,
            COALESCE(f.catId IS NOT NULL, 0) as isFavorite
        FROM search s
        LEFT JOIN favorites f ON s.id = f.catId
    """)
    fun getSearchCatsData(): Flow<List<SearchCatEntity>>

}

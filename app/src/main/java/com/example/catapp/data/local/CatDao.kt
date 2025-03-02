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

    /**
     * Retrieves all cats along with their favorite status.
     *
     * - Uses a `Transaction` to ensure data consistency.
     * - Returns a `Flow` for real-time updates.
     */
    @Transaction
    @Query("SELECT * FROM cats")
    fun getAllCatsWithFavorites(): Flow<List<CatWithFavorite>>

    /**
     * Inserts a list of cats into the database.
     *
     * - Uses `OnConflictStrategy.IGNORE` to avoid duplicates.
     * - This prevents overwriting existing cats with the same ID.
     *
     * @param cats The list of `CatEntity` objects to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCats(cats: List<CatEntity>)

    /**
     * Adds a cat to the favorites table.
     *
     * - Uses `OnConflictStrategy.REPLACE` to update if already favorited.
     * - Ensures the latest favorite status is always stored.
     *
     * @param favorite The `FavoriteEntity` object representing a favorite cat.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favorite: FavoriteEntity)

    /**
     * Removes a cat from the favorites table based on its ID.
     *
     * @param catId The ID of the cat to remove from favorites.
     */
    @Query("DELETE FROM favorites WHERE catId = :catId")
    suspend fun removeFromFavorites(catId: String)

    /**
     * Retrieves only the cats that have been favorited.
     *
     * - Performs an INNER JOIN between `cats` and `favorites` tables.
     * - Returns only the cats present in both tables.
     * - Uses a `Transaction` for data consistency.
     *
     * @return A `Flow` containing a list of favorite cats.
     */
    @Transaction
    @Query("SELECT * FROM cats INNER JOIN favorites ON cats.id = favorites.catId")
    fun getFavoriteCats(): Flow<List<CatWithFavorite>>

    /**
     * Inserts a list of search results (cats) into the database.
     *
     * - Uses `OnConflictStrategy.REPLACE` to update existing search results.
     *
     * @param searchCats The list of `SearchCatEntity` objects to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchCats(searchCats: List<SearchCatEntity>)

    /**
     * Clears all search results from the database.
     */
    @Query("DELETE FROM search")
    suspend fun clearSearchCats()

    /**
     * Retrieves search results along with their favorite status.
     *
     * - Performs a LEFT JOIN between `search` and `favorites` tables.
     * - If a cat exists in the `favorites` table, `isFavorite` will be `true`, otherwise `false`.
     * - Uses `COALESCE` to handle NULL values from the join operation.
     * - Returns a `Flow` to ensure real-time updates when the data changes.
     *
     * @return A `Flow` containing a list of search results with favorite status.
     */
    @Transaction
    @Query("""
        SELECT 
            s.id, 
            s.name, 
            s.breedDescription, 
            s.url,
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

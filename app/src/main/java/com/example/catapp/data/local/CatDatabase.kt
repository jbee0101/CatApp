package com.example.catapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The Room database for storing cat-related data.
 *
 * - Defines the database schema, including the list of entities.
 * - Provides access to DAO for database operations.
 * - `exportSchema = false` to prevent Room from exporting the schema (can be set to `true` for versioning).
 *
 * @property catDao The Data Access Object (DAO) for interacting with the database.
 */
@Database(
    entities = [CatEntity::class, FavoriteEntity::class, SearchCatEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CatDatabase : RoomDatabase() {

    /**
     * Provides an instance of `CatDao` for accessing database operations.
     *
     * @return The DAO instance for cats and favorites.
     */
    abstract fun catDao(): CatDao
}

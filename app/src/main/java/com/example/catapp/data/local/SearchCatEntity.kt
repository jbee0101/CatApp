package com.example.catapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a search result entry for cats in the local database.
 *
 * - This table stores cat data retrieved from search queries.
 * - Helps in caching search results for quick access and offline usage.
 * - Contains all essential details about a cat, including its breed, description, and image URL.
 * - Also tracks whether the cat is marked as a favorite.
 *
 * @property id Unique identifier for the cat (serves as the primary key).
 * @property name The name of the cat breed.
 * @property url The URL of the cat's image.
 * @property breedDescription A short description of the breed.
 * @property breedLifeSpan Expected lifespan range of the breed.
 * @property breedOrigin The country or region where the breed originates.
 * @property breedTemperament Describes the typical temperament of the breed.
 * @property breedUrl A link to more information about the breed.
 * @property isFavorite Indicates whether the cat is marked as a favorite.
 */
@Entity(tableName = "search")
data class SearchCatEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val url: String,
    val breedDescription: String,
    val breedLifeSpan: String,
    val breedOrigin: String,
    val breedTemperament: String,
    val breedUrl: String,
    val isFavorite: Boolean
)


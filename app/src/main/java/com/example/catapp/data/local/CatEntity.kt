package com.example.catapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a cat entity stored in the local database.
 *
 * - Stores detailed information about a cat breed.
 * - This entity is used for caching cat data retrieved from the API.
 * - It includes details such as name, image URL, breed characteristics, and favorite status.
 *
 * @property id Unique identifier for the cat (serves as the primary key).
 * @property name The display name of the cat breed.
 * @property url The image URL of the cat.
 * @property breedDescription A brief description of the breed.
 * @property breedLifeSpan The average lifespan of the breed.
 * @property breedOrigin The geographical origin of the breed.
 * @property breedTemperament The temperament and personality traits of the breed.
 * @property breedUrl The URL containing more information about the breed.
 * @property isFavorite Indicates whether the cat is marked as a favorite.
 */
@Entity(tableName = "cats")
data class CatEntity(
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


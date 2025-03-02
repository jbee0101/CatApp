package com.example.catapp.domain.model

/**
 * A data class representing a cat's information in the domain layer.
 * This class holds the business logic data used within the app.
 *
 * @property id The unique identifier for the cat, usually associated with the breed.
 * @property name The name of the cat breed.
 * @property url A URL pointing to an image of the cat.
 * @property breedDescription A detailed description of the breed's characteristics.
 * @property breedLifeSpan The typical lifespan of the breed.
 * @property breedOrigin The country or region where the breed originated.
 * @property breedTemperament The general temperament or personality of the breed.
 * @property breedUrl A URL to additional breed-related information, like a Wikipedia page.
 * @property isFavorite A flag indicating whether the cat is marked as a favorite by the user.
 */
data class Cat(
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


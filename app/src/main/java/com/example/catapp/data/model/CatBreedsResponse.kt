package com.example.catapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing the response from the Cat Breeds API.
 *
 * This class maps the JSON response from the API into the Kotlin model used in the app.
 * The `@SerializedName` annotations are used to map the JSON keys to Kotlin properties with
 * different names or to maintain consistency with naming conventions.
 *
 * @property id The unique identifier of the cat breed.
 * @property name The name of the cat breed.
 * @property temperament A description of the breed's temperament (nullable).
 * @property origin The origin of the breed (nullable).
 * @property description A description of the breed (nullable).
 * @property lifeSpan The expected lifespan of the breed (nullable).
 * @property wikipediaUrl The URL to the breed's Wikipedia page (nullable).
 * @property referenceImageId The ID of the breed's reference image (nullable).
 * @property image The image object of the breed (nullable).
 */
data class CatBreedsResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("temperament") val temperament: String?,
    @SerializedName("origin") val origin: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("life_span") val lifeSpan: String?,
    @SerializedName("wikipedia_url") val wikipediaUrl: String?,
    @SerializedName("reference_image_id") val referenceImageId: String?,
    @SerializedName("image") val image: Image?
)


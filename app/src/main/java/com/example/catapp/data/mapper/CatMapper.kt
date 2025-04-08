package com.example.catapp.data.mapper

import com.example.catapp.data.local.CatEntity
import com.example.catapp.data.local.SearchCatEntity
import com.example.catapp.data.model.CatBreedsResponse
import com.example.catapp.data.model.CatWithFavorite
import com.example.catapp.domain.model.Cat
import com.example.catapp.utils.AppGlobal.DUMMY_URL

/**
 * Extension function to map a [CatBreedsResponse] object to a [Cat] domain model.
 *
 * - Transforms API response data into a more manageable domain object.
 * - Provides default values for nullable fields from the API response.
 *
 * @return A [Cat] object populated with relevant data.
 */
fun CatBreedsResponse.toCat(): Cat {
    return Cat (
        id = this.id,
        name = this.name,
        url = this.image?.url ?: DUMMY_URL,
        breedDescription = this.description ?: "No description",
        breedLifeSpan = this.lifeSpan ?: "Unknown",
        breedOrigin = this.origin ?: "Unknown",
        breedTemperament = this.temperament ?: "Unknown",
        breedUrl = this.wikipediaUrl ?: "",
        isFavorite = false
    )
}

/**
 * Extension function to map a [Cat] domain model to a [CatEntity] database entity.
 *
 * - Converts a domain object to a database-compatible entity for Room.
 * - Suitable for storing cat details in a local Room database.
 *
 * @return A [CatEntity] object to be stored in the Room database.
 */
fun Cat.toCatEntity(): CatEntity {
    return CatEntity (
        id = this.id,
        name = this.name,
        url = this.url,
        breedDescription = this.breedDescription,
        breedLifeSpan = this.breedLifeSpan,
        breedOrigin = this.breedOrigin,
        breedTemperament = this.breedTemperament,
        breedUrl = this.breedUrl,
        isFavorite = this.isFavorite
    )
}

/**
 * Extension function to map a [Cat] domain model to a [SearchCatEntity] database entity.
 *
 * - Converts a domain model into a Room-compatible search entity.
 * - Suitable for storing search-specific data in the Room database.
 *
 * @return A [SearchCatEntity] object for storing search results in the database.
 */
fun Cat.toSearchCatEntity(): SearchCatEntity {
    return SearchCatEntity (
        id = this.id,
        name = this.name,
        url = this.url,
        breedDescription = this.breedDescription,
        breedLifeSpan = this.breedLifeSpan,
        breedOrigin = this.breedOrigin,
        breedTemperament = this.breedTemperament,
        breedUrl = this.breedUrl,
        isFavorite = this.isFavorite
    )
}

/**
 * Extension function to map a [CatWithFavorite] object (combining [CatEntity] and [FavoriteEntity]) to a [Cat] domain model.
 *
 * - Combines cat data with its favorite status, which is derived from the presence of a favorite entry.
 *
 * @return A [Cat] object with its `isFavorite` status.
 */
fun CatWithFavorite.toCat(): Cat {
    return Cat (
        id = this.cat.id,
        name = this.cat.name,
        url = this.cat.url,
        breedDescription = this.cat.breedDescription,
        breedLifeSpan = this.cat.breedLifeSpan,
        breedOrigin = this.cat.breedOrigin,
        breedTemperament = this.cat.breedTemperament,
        breedUrl = this.cat.breedUrl,
        isFavorite = this.favorite != null
    )
}

/**
 * Extension function to map a [SearchCatEntity] object to a [Cat] domain model.
 *
 * - Converts a search result entity into a domain model for use in the UI or other business logic.
 *
 * @return A [Cat] object with the search entity's details and its favorite status.
 */
fun SearchCatEntity.toCat(): Cat {
    return Cat (
        id = this.id,
        name = this.name,
        url = this.url,
        breedDescription = this.breedDescription,
        breedLifeSpan = this.breedLifeSpan,
        breedOrigin = this.breedOrigin,
        breedTemperament = this.breedTemperament,
        breedUrl = this.breedUrl,
        isFavorite = this.isFavorite
    )
}
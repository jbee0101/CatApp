package com.example.catapp.data.mapper

import com.example.catapp.data.local.CatEntity
import com.example.catapp.data.local.FavoriteEntity
import com.example.catapp.data.local.SearchCatEntity
import com.example.catapp.data.model.CatBreedsResponse
import com.example.catapp.data.model.CatWithFavorite
import com.example.catapp.domain.model.Cat

fun CatBreedsResponse.toCat(): Cat {
    return Cat (
        id = this.id,
        name = this.name,
        url = this.vetstreet_url ?: "",
        breedDescription = this.description ?: "No description",
        breedLifeSpan = this.life_span ?: "Unknown",
        breedOrigin = this.origin ?: "Unknown",
        breedTemperament = this.temperament ?: "Unknown",
        breedUrl = this.wikipedia_url ?: "",
        isFavorite = false
    )
}

fun Cat.toCatEntity(): CatEntity {
    return CatEntity (
        id = this.id,
        name = this.name,
        url = this.url,
        breedName = this.name,
        breedDescription = this.breedDescription,
        breedLifeSpan = this.breedLifeSpan,
        breedOrigin = this.breedOrigin,
        breedTemperament = this.breedTemperament,
        breedUrl = this.breedUrl,
        isFavorite = this.isFavorite
    )
}

fun Cat.toSearchCatEntity(): SearchCatEntity {
    return SearchCatEntity (
        id = this.id,
        name = this.name,
        url = this.url,
        breedName = this.name,
        breedDescription = this.breedDescription,
        breedLifeSpan = this.breedLifeSpan,
        breedOrigin = this.breedOrigin,
        breedTemperament = this.breedTemperament,
        breedUrl = this.breedUrl,
        isFavorite = this.isFavorite
    )
}

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
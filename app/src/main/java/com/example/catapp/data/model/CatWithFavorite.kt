package com.example.catapp.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.catapp.data.local.CatEntity
import com.example.catapp.data.local.FavoriteEntity

/**
 * Data class that represents a cat along with its favorite status.
 *
 * This class is used to hold a `CatEntity` object along with an optional `FavoriteEntity`.
 * The `@Embedded` annotation is used to embed the `CatEntity` fields directly into the `CatWithFavorite` class,
 * while the `@Relation` annotation is used to establish a relationship between `CatEntity` and `FavoriteEntity`
 * based on the `id` field in `CatEntity` and the `catId` field in `FavoriteEntity`.
 *
 * @property cat The cat entity that holds details about the cat breed.
 * @property favorite The favorite entity that holds information about whether the cat is a favorite (nullable).
 * @property isFavorite A computed property that returns `true` if the cat is favorited (i.e., if `favorite` is not null).
 */
data class CatWithFavorite(
    @Embedded val cat: CatEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "catId"
    )
    val favorite: FavoriteEntity?
) {
    val isFavorite: Boolean
        get() = favorite != null
}



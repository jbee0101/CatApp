package com.example.catapp.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.catapp.data.local.CatEntity
import com.example.catapp.data.local.FavoriteEntity

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


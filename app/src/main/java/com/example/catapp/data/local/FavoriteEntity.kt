package com.example.catapp.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    foreignKeys = [ForeignKey(
        entity = CatEntity::class,
        parentColumns = ["id"],
        childColumns = ["catId"],
        onDelete = CASCADE
    )]
)
data class FavoriteEntity(
    @PrimaryKey val catId: String
)
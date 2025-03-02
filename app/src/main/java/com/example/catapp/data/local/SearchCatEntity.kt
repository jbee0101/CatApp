package com.example.catapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
data class SearchCatEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val url: String,
    val breedName: String,
    val breedDescription: String,
    val breedLifeSpan: String,
    val breedOrigin: String,
    val breedTemperament: String,
    val breedUrl: String,
    val isFavorite: Boolean
)


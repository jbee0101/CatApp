package com.example.catapp.domain.model

data class Cat(
    val id: String,
    val name: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breedDescription: String,
    val breedLifeSpan: String,
    val breedOrigin: String,
    val breedTemperament: String,
    val breedUrl: String
)


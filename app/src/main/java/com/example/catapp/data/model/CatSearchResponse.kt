package com.example.catapp.data.model

data class CatSearchResponse(
    val breeds: List<Breed>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)

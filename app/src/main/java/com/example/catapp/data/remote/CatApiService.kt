package com.example.catapp.data.remote

import com.example.catapp.data.model.CatBreedsResponse
import com.example.catapp.data.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApiService {
    @GET("breeds")
    suspend fun getCatBreeds(): List<CatBreedsResponse>

    @GET("images/{image_id}")
    suspend fun getCatImage(@Path("image_id") imageId: String): ImageResponse

    @GET("breeds/search")
    suspend fun searchCats(@Query("q") query: String): List<CatBreedsResponse>
}
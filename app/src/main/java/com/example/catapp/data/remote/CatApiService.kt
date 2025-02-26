package com.example.catapp.data.remote

import com.example.catapp.data.model.CatSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("images/search")
    suspend fun getCatImages(
        @Query("limit") limit : Int = 20,
        @Query("page") page: Int = 0,
        @Query("has_breeds") hasBreeds: Boolean = true,
    ): List<CatSearchResponse>
}
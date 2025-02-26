package com.example.catapp.di

import com.example.catapp.data.local.CatDao
import com.example.catapp.data.remote.CatApiService
import com.example.catapp.data.repository.CatRepositoryImpl
import com.example.catapp.domain.repository.CatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCatRepository(
        catApiService: CatApiService,
        catDao: CatDao
    ): CatRepository {
        return CatRepositoryImpl(catApiService, catDao)
    }
}

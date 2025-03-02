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

/**
 * Dagger module that provides the repository  dependencies
 * The module will be installed in the SingletonComponent for app-wide singletons
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Provides a singleton instance of the [CatRepository] interface.
     * This method creates and returns an instance of [CatRepositoryImpl].
     *
     * @param catApiService The [CatApiService] instance used for making network API calls.
     * @param catDao The [CatDao] instance used for local database operations.
     * @return A [CatRepository] instance, which is an implementation of the repository interface.
     */
    @Provides
    @Singleton
    fun provideCatRepository(
        catApiService: CatApiService,
        catDao: CatDao
    ): CatRepository {
        return CatRepositoryImpl(catApiService, catDao)
    }
}

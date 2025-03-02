package com.example.catapp.di

import com.example.catapp.domain.usecase.*
import com.example.catapp.domain.repository.CatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger module that provides the use cases  dependencies
 * The module will be installed in the SingletonComponent for app-wide singletons
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    /**
     * Provides a singleton instance of [GetAllCatsUseCase].
     *
     * @param catRepository The [CatRepository] instance used to access data.
     * @return A [GetAllCatsUseCase] instance that can be used to fetch all cats.
     */
    @Provides
    fun provideGetAllCatsUseCase(catRepository: CatRepository): GetAllCatsUseCase {
        return GetAllCatsUseCase(catRepository)
    }

    /**
     * Provides a singleton instance of [GetFavoriteCatsUseCase].
     *
     * @param catRepository The [CatRepository] instance used to access data.
     * @return A [GetFavoriteCatsUseCase] instance that can be used to fetch favorite cats.
     */
    @Provides
    fun provideGetFavoriteCatsUseCase(catRepository: CatRepository): GetFavoriteCatsUseCase {
        return GetFavoriteCatsUseCase(catRepository)
    }

    /**
     * Provides a singleton instance of [FetchCatsUseCase].
     *
     * @param catRepository The [CatRepository] instance used to access data.
     * @return A [FetchCatsUseCase] instance that can be used to fetch all cats from the API and save them to the database.
     */
    @Provides
    fun provideFetchCatsUseCase(catRepository: CatRepository): FetchCatsUseCase {
        return FetchCatsUseCase(catRepository)
    }

    /**
     * Provides a singleton instance of [ToggleFavoriteUseCase].
     *
     * @param catRepository The [CatRepository] instance used to access data.
     * @return A [ToggleFavoriteUseCase] instance that can be used to toggle a cat's favorite status.
     */
    @Provides
    fun provideToggleFavoriteUseCase(catRepository: CatRepository): ToggleFavoriteUseCase {
        return ToggleFavoriteUseCase(catRepository)
    }

    /**
     * Provides a singleton instance of [SearchCatsUseCase].
     *
     * @param catRepository The [CatRepository] instance used to access data.
     * @return A [SearchCatsUseCase] instance that can be used to search for cats by a query.
     */
    @Provides
    fun provideSearchCatsUseCase(catRepository: CatRepository): SearchCatsUseCase {
        return SearchCatsUseCase(catRepository)
    }

    /**
     * Provides a singleton instance of [GetSearchCatsUseCase].
     *
     * @param catRepository The [CatRepository] instance used to access data.
     * @return A [GetSearchCatsUseCase] instance that can be used to fetch search results for cats.
     */
    @Provides
    fun provideGetSearchCatsUseCase(catRepository: CatRepository): GetSearchCatsUseCase {
        return GetSearchCatsUseCase(catRepository)
    }
}
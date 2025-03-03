package com.example.catapp.domain.usecase

import com.example.catapp.domain.repository.CatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class ToggleFavoriteUseCaseTest {

    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private val catRepository: CatRepository = mock()

    @Before
    fun setUp() {
        toggleFavoriteUseCase = ToggleFavoriteUseCase(catRepository)
    }

    /**
     * This test ensures that the ToggleFavoriteUseCase correctly invokes the repository's toggleFavorite method
     * with the provided cat Id and the new favorite status, ensuring the favorite status toggle functionality is handled.
     */
    @Test
    fun `invoke should call repository's toggleFavorite method`() = runTest {
        val catId = "1"
        val isFavorite = true

        toggleFavoriteUseCase.invoke(catId, isFavorite)

        verify(catRepository, times(1)).toggleFavorite(catId, isFavorite)
    }
}

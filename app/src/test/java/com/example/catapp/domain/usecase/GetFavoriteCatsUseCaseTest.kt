package com.example.catapp.domain.usecase

import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.repository.CatRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFavoriteCatsUseCaseTest {

    private lateinit var getFavoriteCatsUseCase: GetFavoriteCatsUseCase
    private val catRepository: CatRepository = mock()

    @Before
    fun setUp() {
        getFavoriteCatsUseCase = GetFavoriteCatsUseCase(catRepository)
    }

    /**
     * This test ensures that the GetFavoriteCatsUseCase correctly fetches the list of favorite cats
     * from the repository and emits the expected list of cats when invoked.
     */
    @Test
    fun `invoke should return list of favorite cats from repository`() = runTest {
        val favoriteCats = listOf(
            Cat(
                id = "1",
                name = "Catty",
                url = "url1",
                breedOrigin = "EU",
                breedLifeSpan = "13",
                breedTemperament = "Angry",
                breedDescription = "Angry Cat",
                breedUrl = "wiki",
                isFavorite = true
            )
        )

        whenever(catRepository.getFavoriteCats()).thenReturn(flowOf(favoriteCats))

        val resultFlow = getFavoriteCatsUseCase.invoke()

        resultFlow.collect { cats ->
            assertEquals(favoriteCats, cats)
        }

        verify(catRepository, times(1)).getFavoriteCats()
    }
}

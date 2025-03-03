package com.example.catapp.domain.usecase

import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.repository.CatRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetSearchCatsUseCaseTest {

    private lateinit var getSearchCatsUseCase: GetSearchCatsUseCase
    private val catRepository: CatRepository = mock()

    @Before
    fun setUp() {
        getSearchCatsUseCase = GetSearchCatsUseCase(catRepository)
    }

    /**
     * This test ensures that the GetSearchCatsUseCase correctly fetches the list of search cats
     * from the repository and emits the expected list of cats when invoked.
     */
    @Test
    fun `invoke should return list of search cats from repository`() = runTest {
        val searchCats = listOf(
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

        whenever(catRepository.getSearchCats()).thenReturn(flowOf(searchCats))

        val resultFlow = getSearchCatsUseCase.invoke()

        resultFlow.collect { cats ->
            assertEquals(searchCats, cats)
        }

        verify(catRepository, times(1)).getSearchCats()
    }
}

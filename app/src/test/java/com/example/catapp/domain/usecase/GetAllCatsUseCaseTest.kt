package com.example.catapp.domain.usecase
import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.repository.CatRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllCatsUseCaseTest {

    private lateinit var getAllCatsUseCase: GetAllCatsUseCase
    private val catRepository: CatRepository = mockk()

    /**
     * Initialize the use case with the mocked repository
     */
    @Before
    fun setUp() {
        getAllCatsUseCase = GetAllCatsUseCase(catRepository)
    }

    /**
     * It mocks the repository to return a predefined list of cats and verifies that the use case emits the same list via the flow.
     */
    @Test
    fun `test invoke returns cats list from repository`() = runBlocking {
        val mockCatsList = listOf(
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
            ),
            Cat(
                id = "2",
                name = "Fluffy",
                url = "url2",
                breedOrigin = "US",
                breedLifeSpan = "10",
                breedTemperament = "Friendly",
                breedDescription = "Friendly Cat",
                breedUrl = "wiki",
                isFavorite = false
            )
        )

        coEvery { catRepository.getAllCats() } returns flowOf(mockCatsList)

        val result = getAllCatsUseCase()

        // Verify that the flow returns the correct list of cats
        result.collect { catsList ->
            assertEquals(mockCatsList, catsList)
        }
    }
}

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
class SearchCatsUseCaseTest {

    private lateinit var searchCatsUseCase: SearchCatsUseCase
    private val catRepository: CatRepository = mock()

    @Before
    fun setUp() {
        searchCatsUseCase = SearchCatsUseCase(catRepository)
    }

    /**
     * This test ensures that the SearchCatsUseCase correctly invokes the repository's searchCats method
     * with the provided query string and performs the search operation as expected.
     */
    @Test
    fun `invoke should call repository's searchCats method`() = runTest {
        val query = "Persian"

        searchCatsUseCase.invoke(query)

        verify(catRepository, times(1)).searchCats(query)
    }
}

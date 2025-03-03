package com.example.catapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.usecase.FetchCatsUseCase
import com.example.catapp.domain.usecase.GetAllCatsUseCase
import com.example.catapp.domain.usecase.GetFavoriteCatsUseCase
import com.example.catapp.domain.usecase.GetSearchCatsUseCase
import com.example.catapp.domain.usecase.SearchCatsUseCase
import com.example.catapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class CatViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockCatData = Cat(
        id = "1",
        url = "url1",
        name = "Catty",
        breedOrigin = "EU",
        breedLifeSpan = "13",
        breedTemperament = "Angry",
        breedDescription = "Angry Cat",
        breedUrl = "wiki",
        isFavorite = true
    )

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CatViewModel
    private val getAllCatsUseCase: GetAllCatsUseCase = mock()
    private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase = mock()
    private val fetchCatsUseCase: FetchCatsUseCase = mock()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mock()
    private val searchCatsUseCase: SearchCatsUseCase = mock()
    private val getSearchCatsUseCase: GetSearchCatsUseCase = mock()

    /**
     * Setup method to initialize ViewModel before each test
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CatViewModel(
            dispatcher = testDispatcher,
            getAllCatsUseCase,
            getFavoriteCatsUseCase,
            fetchCatsUseCase,
            toggleFavoriteUseCase,
            searchCatsUseCase,
            getSearchCatsUseCase
        )
    }

    /**
     * Teardown method to reset the dispatcher after each test
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Test to verify that the 'favoriteCats' LiveData is updated correctly when 'fetchFavoriteCats' is called
     */
    @Test
    fun `fetchFavoriteCats updates favoriteCats LiveData`() = runTest {
        val favoriteCatsList = listOf(mockCatData)

        `when`(getFavoriteCatsUseCase.invoke()).thenReturn(flowOf(favoriteCatsList))

        val observer: Observer<List<Cat>> = mock()
        viewModel.favoriteCats.observeForever(observer)

        viewModel.fetchFavoriteCats()

        advanceUntilIdle()

        verify(observer).onChanged(favoriteCatsList)
    }

    /**
     * Test to verify that the 'toggleFavorite' function calls the appropriate use case
     */
    @Test
    fun `toggleFavorite calls use case`() = runTest {
        val catId = "1"
        val isFavorite = true

        viewModel.toggleFavorite(catId, isFavorite)

        advanceUntilIdle()

        verify(toggleFavoriteUseCase, times(1)).invoke(catId, isFavorite)
    }

    /**
     * Test to verify that 'onSearchQueryChanged' correctly updates the search query in the ViewModel
     */
    @Test
    fun `onSearchQueryChanged updates searchQuery`() {
        val query = "Persian"

        viewModel.onSearchQueryChanged(query)

        assert(viewModel.searchQuery.value == query)
    }
}

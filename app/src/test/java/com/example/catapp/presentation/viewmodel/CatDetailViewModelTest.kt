package com.example.catapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class CatDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CatDetailViewModel
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mock()

    /**
     * Setup method to initialize ViewModel before each test
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CatDetailViewModel(
            toggleFavoriteUseCase
        )
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

}
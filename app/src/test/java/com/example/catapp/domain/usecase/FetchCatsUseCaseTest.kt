package com.example.catapp.domain.usecase

import com.example.catapp.domain.repository.CatRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchCatsUseCaseTest {

    private lateinit var fetchCatsUseCase: FetchCatsUseCase
    private val catRepository: CatRepository = mockk()

    /**
     * Initialize the use case with the mocked repository
     */
    @Before
    fun setUp() {
        fetchCatsUseCase = FetchCatsUseCase(catRepository)
    }


    /**
     * Verify the repository's fetchCats method was called
     */
    @Test
    fun `test invoke fetchCats calls repository fetchCats`() = runBlocking {

        coEvery { catRepository.fetchCats() } returns Unit

        fetchCatsUseCase()

        coVerify { catRepository.fetchCats() }
    }
}

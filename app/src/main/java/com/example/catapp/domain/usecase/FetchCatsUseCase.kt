package com.example.catapp.domain.usecase

import com.example.catapp.domain.repository.CatRepository
import javax.inject.Inject

/**
 * Use case responsible for fetching cats from the remote API and saving them to the local database.
 * This use case abstracts the interaction between the repository and the external data source
 * and is called by the ViewModel or other business logic layers.
 */
class FetchCatsUseCase @Inject constructor(
    private val catRepository: CatRepository
) {

    /**
     * Executes the use case by invoking the [fetchCats] method on the repository.
     * This method triggers the fetching of cats from the API and saves them to the database.
     */
    suspend operator fun invoke() {
        catRepository.fetchCats()
    }
}

package com.example.catapp.domain.usecase

import com.example.catapp.domain.repository.CatRepository
import javax.inject.Inject

/**
 * Use case for searching cats based on a query string.
 *
 * @param catRepository The repository used to perform the search operation. Injected by Dagger.
 */
class SearchCatsUseCase @Inject constructor(
    private val catRepository: CatRepository
) {
    /**
     * Invokes the use case to search for cats using the provided query string.
     *
     * @param query The search query to filter the cats.
     */
    suspend operator fun invoke(query: String) {
        catRepository.searchCats(query)
    }
}

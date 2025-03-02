package com.example.catapp.domain.usecase

import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching all the favorite cats from the repository.
 *
 * @param catRepository The repository used to fetch the data. Injected by Dagger.
 */
class GetFavoriteCatsUseCase @Inject constructor(
    private val catRepository: CatRepository
) {
    /**
     * Invokes the use case to fetch the list of favorite cats.
     *
     * @return A [Flow] that emits a list of [Cat] objects which represent the favorite cats.
     */
    suspend operator fun invoke(): Flow<List<Cat>> {
        return catRepository.getFavoriteCats()
    }
}

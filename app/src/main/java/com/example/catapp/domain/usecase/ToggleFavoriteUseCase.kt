package com.example.catapp.domain.usecase

import com.example.catapp.domain.repository.CatRepository
import javax.inject.Inject

/**
 * Use case for toggling a cat's favorite status.
 *
 * @param catRepository The repository used to perform the toggle favorite operation. Injected by Dagger.
 */
class ToggleFavoriteUseCase @Inject constructor(
    private val catRepository: CatRepository
) {
    /**
     * Invokes the use case to toggle the favorite status of a cat.
     *
     * @param catId The ID of the cat whose favorite status is to be toggled.
     * @param isFavorite The new favorite status of the cat.
     */
    suspend operator fun invoke(catId: String, isFavorite: Boolean) {
        catRepository.toggleFavorite(catId, isFavorite)
    }
}

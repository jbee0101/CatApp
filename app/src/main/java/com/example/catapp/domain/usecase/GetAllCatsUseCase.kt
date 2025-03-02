package com.example.catapp.domain.usecase

import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case responsible for retrieving all cats from the local database.
 * This use case is used to retrieve the list of all cats that have been saved locally.
 * It abstracts the interaction between the ViewModel and the repository layer.
 */
class GetAllCatsUseCase @Inject constructor(
    private val catRepository: CatRepository
) {

    /**
     * Executes the use case by invoking the [getAllCats] method on the repository.
     * This method returns a flow of the list of all cats.
     * The flow is used to observe changes to the list of cats in the local database.
     */
    suspend operator fun invoke(): Flow<List<Cat>> {
        return catRepository.getAllCats()
    }
}

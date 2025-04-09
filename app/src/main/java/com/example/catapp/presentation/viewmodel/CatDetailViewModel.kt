package com.example.catapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the toggle state for favorite cats in CatDetailScreen.
 *
 * @param toggleFavoriteUseCase Use case for toggling the favorite status of a cat.
 */
@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
): ViewModel() {

    /**
     * Toggles the favorite status of a specific cat.
     * @param catId The ID of the cat to update.
     * @param isFavorite The new favorite status of the cat.
     */
    fun toggleFavorite(catId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase.invoke(catId, isFavorite)
        }
    }
}
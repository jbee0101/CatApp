package com.example.catapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.di.MainDispatcher
import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.usecase.GetFavoriteCatsUseCase
import com.example.catapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and business logic for favorite cat-related data in the CatApp.
 *
 * This ViewModel interacts with multiple use cases to fetch favorite cats and manage the favorite status of cats.
 * It provides LiveData and StateFlow to handle the reactive UI updates in response to data changes.
 *
 * @param dispatcher Main dispatcher for updating states value.
 * @param getFavoriteCatsUseCase Use case for fetching the list of favorite cats.
 * @param toggleFavoriteUseCase Use case for toggling the favorite status of a cat.
 */
@HiltViewModel
class FavoriteCatViewModel @Inject constructor(
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
    private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
): ViewModel() {

    private val _favoriteCats = MutableLiveData<List<Cat>>(emptyList())
    val favoriteCats: LiveData<List<Cat>> get() = _favoriteCats

    private val _uiState = MutableStateFlow(true)
    val uiState: StateFlow<Boolean> get() = _uiState

    /**
     * Fetches the list of favorite cats using the GetFavoriteCatsUseCase.
     * Updates the _favoriteCats LiveData with the result.
     */
    fun fetchFavoriteCats() {
        viewModelScope.launch {
            try {
                getFavoriteCatsUseCase.invoke().collect { favList ->
                    _favoriteCats.postValue(favList)
                }
            }  catch (e: IOException) {
                withContext(dispatcher) { _uiState.value = false }
            }
        }
    }

    /**
     * Toggles the favorite status of a specific cat.
     * @param catId The ID of the cat to update.
     * @param isFavorite The new favorite status of the cat.
     */
    fun toggleFavorite(catId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase.invoke(catId, isFavorite)
            }  catch (e: IOException) {
                withContext(dispatcher) { _uiState.value = false }
            }
        }
    }

    /**
     * Refresh the screen by calling getFavoriteCatsUseCase for FavoriteCatScreen.
     * Updates the _cats LiveData with the result.
     */
    fun onRefreshUi() {
        viewModelScope.launch {
            _uiState.value = true
            fetchFavoriteCats()
        }
    }
}
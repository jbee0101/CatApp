package com.example.catapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.usecase.FetchCatsUseCase
import com.example.catapp.domain.usecase.GetAllCatsUseCase
import com.example.catapp.domain.usecase.GetFavoriteCatsUseCase
import com.example.catapp.domain.usecase.GetSearchCatsUseCase
import com.example.catapp.domain.usecase.SearchCatsUseCase
import com.example.catapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and business logic for cat-related data in the CatApp.
 *
 * This ViewModel interacts with multiple use cases to fetch, search, and manage cats, as well as manage the favorite status of cats.
 * It provides LiveData and StateFlow to handle the reactive UI updates in response to data changes, including search queries and loading states.
 *
 * @param getAllCatsUseCase Use case for fetching all available cats.
 * @param getFavoriteCatsUseCase Use case for fetching the list of favorite cats.
 * @param fetchCatsUseCase Use case for fetching fresh cat data from a remote source.
 * @param toggleFavoriteUseCase Use case for toggling the favorite status of a cat.
 * @param searchCatsUseCase Use case for performing a search for cats based on a query.
 * @param getSearchCatsUseCase Use case for fetching search results based on the current search query.
 */
@OptIn(FlowPreview::class)
@HiltViewModel
class CatViewModel @Inject constructor(
    private val getAllCatsUseCase: GetAllCatsUseCase,
    private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase,
    private val fetchCatsUseCase: FetchCatsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val searchCatsUseCase: SearchCatsUseCase,
    private val getSearchCatsUseCase: GetSearchCatsUseCase
) : ViewModel() {

    private val _cats = MutableLiveData<List<Cat>>(emptyList())
    val cats: LiveData<List<Cat>> get() = _cats

    private val _favoriteCats = MutableLiveData<List<Cat>>(emptyList())
    val favoriteCats: LiveData<List<Cat>> get() = _favoriteCats

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    /**
     * Initializes the ViewModel, listens for changes to the search query, and triggers appropriate actions:
     * - Searches for cats if a query is provided.
     * - Fetches all cats if no query is provided and the local cache is empty.
     */
    init {
        viewModelScope.launch(Dispatchers.IO) {
            _searchQuery.debounce(500).distinctUntilChanged().collectLatest { query ->
                    if (query.isNotEmpty()) {
                        withContext(Dispatchers.Main) { _isLoading.value = true }
                        searchCatsUseCase.invoke(query)
                        getSearchCatData()
                        withContext(Dispatchers.Main) { _isLoading.value = false }
                    } else {
                        getAllCatsUseCase.invoke().collect { catsList ->
                                if (catsList.isEmpty()) {
                                    fetchCats()
                                } else {
                                    withContext(Dispatchers.Main) {
                                        _cats.value = catsList
                                    }
                                }
                            }
                    }
                }
        }
    }

    /**
     * Fetches fresh cat data using the FetchCatsUseCase.
     * This is triggered when no local cats are found or when the user has no search query.
     */
    private fun fetchCats() {
        viewModelScope.launch {
            _isLoading.value = true
            Log.d("ViewModel", "fetchCats method called")
            fetchCatsUseCase.invoke()
            _isLoading.value = false
        }
    }

    /**
     * Fetches the list of favorite cats using the GetFavoriteCatsUseCase.
     * Updates the _favoriteCats LiveData with the result.
     */
    fun fetchFavoriteCats() {
        viewModelScope.launch {
            getFavoriteCatsUseCase.invoke().collect { favList ->
                _favoriteCats.postValue(favList)
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
            toggleFavoriteUseCase.invoke(catId, isFavorite)
        }
    }

    /**
     * Updates the search query stored in _searchQuery.
     * This will trigger the search operation based on the new query.
     * @param newQuery The new search query to filter cats.
     */
    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    /**
     * Fetches the list of cats based on the current search query using the GetSearchCatsUseCase.
     * Updates the _cats LiveData with the result.
     */
    private fun getSearchCatData() {
        viewModelScope.launch {
            getSearchCatsUseCase.invoke().collect { searchList ->
                withContext(Dispatchers.Main) {
                    _cats.value = searchList
                }
            }
        }
    }
}



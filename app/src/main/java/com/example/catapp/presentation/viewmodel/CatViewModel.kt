package com.example.catapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.repository.CatRepository
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


@OptIn(FlowPreview::class)
@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel() {

    private val _cats = MutableLiveData<List<Cat>>(emptyList())
    val cats: LiveData<List<Cat>> get() = _cats

    private val _favoriteCats = MutableLiveData<List<Cat>>(emptyList())
    val favoriteCats: LiveData<List<Cat>> get() = _favoriteCats

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _searchQuery.debounce(500).distinctUntilChanged().collectLatest { query ->
                    if (query.isNotEmpty()) {
                        withContext(Dispatchers.Main) { _isLoading.value = true }
                        repository.searchCats(query)
                        getSearchCatData()
                        withContext(Dispatchers.Main) { _isLoading.value = false }
                    } else {
                        repository.getAllCats().collect { catsList ->
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

    fun fetchCats() {
        viewModelScope.launch {
            _isLoading.value = true
            Log.d("ViewModel", "fetchCats method called")
            repository.fetchCats()
            _isLoading.value = false
        }
    }

    fun fetchFavoriteCats() {
        viewModelScope.launch {
            repository.getFavoriteCats().collect { favList ->
                _favoriteCats.postValue(favList)
            }
        }
    }

    fun toggleFavorite(catId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(catId, isFavorite)
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    private fun getSearchCatData() {
        viewModelScope.launch {
            repository.getSearchCats().collect { searchList ->
                withContext(Dispatchers.Main) {
                    _cats.value = searchList
                }
            }
        }
    }
}



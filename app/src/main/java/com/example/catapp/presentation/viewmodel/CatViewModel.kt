package com.example.catapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.repository.CatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


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

    private var currentPage = 0
    private var pageLimit = 15

    init {
        viewModelScope.launch {
            repository.getAllCats()
                .collect { catsList ->
                    if (catsList.isEmpty()) {
                        fetchCats(currentPage, pageLimit)
                    } else {
                        _cats.value = catsList
                    }
                }
        }
    }

    fun fetchCats(page: Int, limit: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            Log.d("ViewModel", "fetchCats method called with page: $page, limit: $limit")
            repository.getCats(page, limit)
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
}



package com.example.catapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.catapp.domain.model.Cat
import com.example.catapp.domain.repository.CatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel() {

//    private val _cats = MutableLiveData<List<Cat>>()
//    val cats: LiveData<List<Cat>> = _cats

    private val _cats = MutableLiveData<List<Cat>>(emptyList())  // StateFlow to hold data
    val cats: LiveData<List<Cat>> get() = _cats

    private var currentPage = 0
    private var pageLimit = 12

    init {
        viewModelScope.launch {
            repository.getAllCats()
                .collect { catsList ->
                    if (catsList.isEmpty()) {
                        // If the database is empty, fetch the first page from API
                        fetchCats(currentPage, pageLimit)
                    } else {
                        _cats.value = catsList
                    }
                }
        }
    }

    fun loadMore() {
        currentPage++
        fetchCats(currentPage, pageLimit)
    }

    fun fetchCats(page: Int, limit: Int) {
        viewModelScope.launch {
            Log.d("ViewModel", "fetchCats method called with page: $page, limit: $limit")
            repository.getCats(page, limit)
        }
    }

    fun toggleFavorite(catId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(catId, isFavorite)
        }
    }
}



package com.example.catapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.catapp.presentation.ui.navigation.Screen
import com.example.catapp.presentation.viewmodel.CatViewModel

@Composable
fun FavoriteCatsScreen(navController: NavController) {
    val viewModel: CatViewModel = hiltViewModel()
    val favoriteCats by viewModel.favoriteCats.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchFavoriteCats()
    }

    if (favoriteCats.isEmpty()) {
        Text(
            text = "No favorite cats yet!",
            modifier = Modifier.fillMaxSize().padding(top = 200.dp),
            textAlign = TextAlign.Center
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favoriteCats) { cat ->
                CatGridItem (cat, true, viewModel::toggleFavorite) {
                    navController.navigate(
                        Screen.CatDetails.createRoute(
                            catId = cat.id,
                            catImageUrl = cat.url,
                            catName = cat.name,
                            catOrigin = cat.breedOrigin,
                            catLifeSpan = cat.breedLifeSpan,
                            catTemperament = cat.breedTemperament,
                            catDescription = cat.breedDescription,
                            isFavorite = cat.isFavorite
                        )
                    )
                }
            }
        }
    }
}
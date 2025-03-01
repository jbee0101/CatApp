package com.example.catapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.catapp.R
import com.example.catapp.domain.model.Cat
import com.example.catapp.presentation.viewmodel.CatViewModel

@Composable
fun CatListScreen(navController: NavController) {

    val viewModel: CatViewModel = hiltViewModel()
    val cats by viewModel.cats.observeAsState(emptyList())
    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(state = listState) {
            items(cats) { cat ->
                CatItem(cat, viewModel::toggleFavorite)
            }
        }
        // Detect when the user has scrolled to the bottom
        LaunchedEffect(listState.firstVisibleItemIndex) {
            if (listState.layoutInfo.visibleItemsInfo.isNotEmpty() &&
                listState.layoutInfo.visibleItemsInfo.last().index == cats.size - 1) {
                // User is at the end of the list, fetch more cats
                viewModel.loadMore()
            }
        }
    }
}

@Composable
fun CatItem(cat: Cat, onFavoriteToggle: (String, Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cat.url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .crossfade(true)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = cat.name,
            modifier = Modifier.size(100.dp)
        )

        Column {
            Text(cat.name)
            IconButton(onClick = { onFavoriteToggle(cat.id, !cat.isFavorite) }) {
                Log.e("UIScreen", "Cat: ${cat.name} Fav: ${cat.isFavorite}")
                Icon(
                    imageVector = if (cat.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite"
                )
            }
        }
    }
}

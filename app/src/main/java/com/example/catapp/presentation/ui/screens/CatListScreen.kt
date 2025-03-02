package com.example.catapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.catapp.R
import com.example.catapp.domain.model.Cat
import com.example.catapp.presentation.ui.navigation.Screen
import com.example.catapp.presentation.viewmodel.CatViewModel
import com.example.catapp.utils.AppGlobal.DUMMY_URL

@Composable
fun CatListScreen(navController: NavController) {

    val viewModel: CatViewModel = hiltViewModel()
    val cats by viewModel.cats.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val listState = rememberLazyGridState()

    Box(modifier = Modifier.fillMaxSize()) {

        if (isLoading && cats.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.statusBars)
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChanged = { viewModel.onSearchQueryChanged(it) }
                )

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                } else {
                    LazyVerticalGrid(
                        state = listState,
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(cats) { cat ->
                            CatGridItem(cat,false, viewModel::toggleFavorite) {
                                navController.navigate(
                                    Screen.CatDetails.createRoute(
                                        catId = cat.id,
                                        catImageUrl = cat.url.ifEmpty { DUMMY_URL },
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
        }
    }
}

@Composable
fun CatGridItem(
    cat: Cat,
    isFavoriteScreen: Boolean = false,
    onFavoriteToggle: (String, Boolean) -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .clickable { onClick() },
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(cat.url)
                                .placeholder(R.drawable.loading)
                                .error(R.drawable.error)
                                .crossfade(true)
                                .build()
                        ),
                        contentDescription = cat.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = ContentScale.Crop
                    )

                    IconButton(
                        onClick = { onFavoriteToggle(cat.id, !cat.isFavorite) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .background(Color.White, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = if (cat.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (cat.isFavorite) Color.Red else Color.Gray
                        )
                    }
                }
                Text(
                    text = cat.name,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2, // Limit to 2 lines
                    overflow = TextOverflow.Ellipsis, // Show ellipsis if text overflows
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                if (isFavoriteScreen) {
                    Text(
                        text = "LifeSpan: ${cat.breedLifeSpan.substringAfter("-")}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = { onQueryChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(2.dp)),
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChanged("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear Search")
                }
            }
        }
    )
}



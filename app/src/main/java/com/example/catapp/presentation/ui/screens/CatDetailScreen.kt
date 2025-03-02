package com.example.catapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.catapp.R
import com.example.catapp.presentation.viewmodel.CatViewModel

@Composable
fun CatDetailScreen(
    catId: String,
    catImageUrl: String,
    catName: String,
    catOrigin: String,
    catTemperament: String,
    catDescription: String,
    isFavorite: Boolean,
    viewModel: CatViewModel,
    onBackPress: () -> Unit
) {
    var favoriteState by remember { mutableStateOf(isFavorite) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = catName,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(16.dp))

        Box(contentAlignment = Alignment.TopEnd) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(catImageUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = "Cat Image",
                modifier = Modifier.size(250.dp),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = {
                    favoriteState = !favoriteState
                    viewModel.toggleFavorite(catId, favoriteState)
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (favoriteState) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (favoriteState) Color.Red else Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card (
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Origin: $catOrigin", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Temperament: $catTemperament", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Description:", fontWeight = FontWeight.Bold)
                Text(text = catDescription)
            }
        }
    }
}

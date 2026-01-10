package com.example.myapplication.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.data.model.MovieModel
import com.example.myapplication.utills.SelectedMovie
import com.example.myapplication.utills.UiState
import com.example.myapplication.viewmodel.FavouriteViewModel
import com.example.myapplication.viewmodel.MovieViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(onClick: () -> Unit = {}) {
    val viewModelMovies: MovieViewModel = koinViewModel()
    val favViewmodel: FavouriteViewModel = koinViewModel()
    val state by viewModelMovies.movies.collectAsState()
    val favourit by favViewmodel.favoriteMovies.collectAsState()
    var isFavourite by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isFavourite) "Favourite Movies" else "All Movies"
                    )
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isFavourite) "Favourite" else "All",
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Switch(
                            checked = isFavourite,
                            onCheckedChange = { isFavourite = it }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        if (!isFavourite) {
                            Text(
                                text = "reload",
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier.clickable {
                                    viewModelMovies.reload()
                                }
                            )
                        }


                    }
                }
            )
        }
    ) { paddingValues ->


        if (isFavourite) {

            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(favourit) { movie ->
                    MovieItem(movie, favouriteViewModel = favViewmodel, viewModelMovies,{
                        onClick.invoke()
                    })
                }
            }
        } else {
            when (state) {

                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Error -> {
                    val message = (state as UiState.Error).message
                    Text(text = message, modifier = Modifier.padding(16.dp))
                }

                is UiState.Success -> {
                    val movies = (state as UiState.Success<List<MovieModel>>).data

                    LazyColumn(modifier = Modifier.padding(paddingValues)) {
                        items(movies) { movie ->
                            MovieItem(movie, favouriteViewModel = favViewmodel,viewModelMovies, {
                                onClick.invoke()
                            })
                        }
                    }
                }
            }
        }


    }


}

@Composable
fun MovieItem(movie: MovieModel, favouriteViewModel: FavouriteViewModel,viewmodel: MovieViewModel, onClick: () -> Unit) {
    val favoriteMovies by favouriteViewModel.favoriteMovies.collectAsState()
    val isFavorite = favoriteMovies.any { it.id == movie.id }
    Card(
        modifier = Modifier
            .padding(12.dp)
            .clickable {
                SelectedMovie.set(movie)
                onClick() }
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.image),
                    contentDescription = movie.title,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(movie.title, style = MaterialTheme.typography.titleMedium)
                    Text("Director: ${movie.director}")
                    Text("Year: ${movie.release_date}")
                }
            }

            Log.e("Mainscreen", "MovieItem: ${movie.isFavorite}")
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                tint = if (isFavorite) Color.Red else Color.Gray,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clickable {
                        favouriteViewModel.toggleFavorite(movie)
                    }
            )
        }
    }
}

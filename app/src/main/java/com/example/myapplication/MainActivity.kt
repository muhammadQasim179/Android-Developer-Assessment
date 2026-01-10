package com.example.myapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.MovieDetail
import com.example.myapplication.ui.MovieDetailScreen
import com.example.myapplication.ui.MovieList
import com.example.myapplication.ui.MovieListScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.utills.SelectedMovie

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {

                MovieApp()
            }
        }
    }
}
@Composable
fun MovieApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MovieList) {
        composable<MovieList> {

            MovieListScreen({

                navController.navigate(MovieDetail)
            })
        }
        composable<MovieDetail> {
            MovieDetailScreen(SelectedMovie.get())
        }
    }
}
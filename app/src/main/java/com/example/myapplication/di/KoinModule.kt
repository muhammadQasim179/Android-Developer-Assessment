package com.example.myapplication.di
import androidx.room.Room
import com.example.myapplication.data.model.AppDatabase
import com.example.myapplication.data.remote.MovieApi
import com.example.myapplication.data.repository.FavouriteRepository
import com.example.myapplication.data.repository.MovieRepository
import com.example.myapplication.viewmodel.FavouriteViewModel
import com.example.myapplication.viewmodel.MovieViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mediaModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://ghibliapi.vercel.app/") // your API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "movie_db"
        ).build()
    }
    single { get<Retrofit>().create(MovieApi::class.java) }

    single { get<AppDatabase>().movieDao() }
    single { FavouriteRepository(get()) }
    viewModel { FavouriteViewModel(get()) }

    single { MovieRepository(get()) }
    viewModel { MovieViewModel(get()) }
}
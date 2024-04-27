package jlmd.android.developer.movieswigitest.views.di

import jlmd.android.developer.movieswigitest.views.movies.viewmodel.MoviesDetailViewModel
import jlmd.android.developer.movieswigitest.views.movies.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { MoviesViewModel(getMoviesUseCase = get(), getFavoritesMoviesUseCase = get(), observeFavoritesMoviesUseCase = get()) }
    viewModel { MoviesDetailViewModel(getMovieByIdUseCase = get(), setFavoriteMovieUseCase = get()) }
}
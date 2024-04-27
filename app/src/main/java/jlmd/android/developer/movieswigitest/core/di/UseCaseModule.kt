package jlmd.android.developer.movieswigitest.core.di

import jlmd.android.developer.movieswigitest.core.usecases.GetFavoritesMoviesUseCase
import jlmd.android.developer.movieswigitest.core.usecases.GetMovieByIdUseCase
import jlmd.android.developer.movieswigitest.core.usecases.GetMoviesUseCase
import jlmd.android.developer.movieswigitest.core.usecases.ObserveFavoritesMoviesUseCase
import jlmd.android.developer.movieswigitest.core.usecases.UpdateFavoriteMovieUseCase
import org.koin.dsl.module

val useCaseModules = module {

    factory { GetMoviesUseCase(moviesRepository = get()) }
    factory { GetMovieByIdUseCase(moviesRepository = get()) }
    factory { GetFavoritesMoviesUseCase(moviesRepository = get()) }
    factory { UpdateFavoriteMovieUseCase(moviesRepository = get()) }
    factory { ObserveFavoritesMoviesUseCase(moviesRepository = get()) }
}
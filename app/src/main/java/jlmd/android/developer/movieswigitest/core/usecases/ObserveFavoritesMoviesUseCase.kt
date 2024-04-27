package jlmd.android.developer.movieswigitest.core.usecases

import jlmd.android.developer.movieswigitest.core.MoviesRepository
import jlmd.android.developer.movieswigitest.core.model.FavoritesMovies
import kotlinx.coroutines.flow.Flow

class ObserveFavoritesMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(): Flow<FavoritesMovies> {
        return moviesRepository.observeFavoritesMovies()
    }
}
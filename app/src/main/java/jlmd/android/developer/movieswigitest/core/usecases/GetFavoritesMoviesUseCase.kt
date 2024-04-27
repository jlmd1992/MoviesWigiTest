package jlmd.android.developer.movieswigitest.core.usecases

import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.core.MoviesRepository
import jlmd.android.developer.movieswigitest.core.model.Movie

class GetFavoritesMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend fun invoke(): RequestResult<List<Movie>> {
        return moviesRepository.getFavoritesMovies()
    }
}
package jlmd.android.developer.movieswigitest.core.usecases

import jlmd.android.developer.movieswigitest.core.model.Movie
import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.core.MoviesRepository

class GetMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend fun invoke(): RequestResult<List<Movie>> {
        return moviesRepository.getMovies()
    }
}
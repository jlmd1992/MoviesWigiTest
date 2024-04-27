package jlmd.android.developer.movieswigitest.core.usecases

import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.core.MoviesRepository
import jlmd.android.developer.movieswigitest.core.model.Movie

class GetMovieByIdUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend fun invoke(movieId: Int): RequestResult<Movie> {
        return moviesRepository.getMovieById(movieId)
    }
}
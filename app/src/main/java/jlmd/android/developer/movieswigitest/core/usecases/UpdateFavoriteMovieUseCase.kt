package jlmd.android.developer.movieswigitest.core.usecases

import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.core.MoviesRepository
import jlmd.android.developer.movieswigitest.views.movies.model.MovieDetail

class UpdateFavoriteMovieUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend fun invoke(movie: MovieDetail): RequestResult.Success<MovieDetail?> {
        val result = moviesRepository.updateMovie(movie)
        return RequestResult.Success(result)
    }
}
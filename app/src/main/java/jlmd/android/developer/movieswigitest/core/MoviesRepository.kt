package jlmd.android.developer.movieswigitest.core

import jlmd.android.developer.movieswigitest.core.model.Movie
import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.core.model.FavoritesMovies
import jlmd.android.developer.movieswigitest.views.movies.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMovies(): RequestResult<List<Movie>>

    suspend fun getMovieById(movieId: Int): RequestResult<Movie>

    suspend fun getFavoritesMovies(): RequestResult<List<Movie>>

    suspend fun updateMovie(movie: MovieDetail): MovieDetail?

    suspend fun getFavoritesMoviesObserve(): List<Movie>

    fun observeFavoritesMovies(): Flow<FavoritesMovies>
}
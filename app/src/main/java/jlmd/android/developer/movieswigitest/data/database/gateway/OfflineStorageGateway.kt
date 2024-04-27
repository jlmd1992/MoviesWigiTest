package jlmd.android.developer.movieswigitest.data.database.gateway

import jlmd.android.developer.movieswigitest.data.database.entities.MovieEntity

interface OfflineStorageGateway {

    suspend fun getMoviesLocal(): List<MovieEntity>?

    suspend fun saveMovies(movies: List<MovieEntity>)

    suspend fun getMovieById(movieId: Int): MovieEntity

    suspend fun getFavoritesMovies(): List<MovieEntity>

    suspend fun updateMovie(movie: MovieEntity)
}
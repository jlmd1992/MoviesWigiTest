package jlmd.android.developer.movieswigitest.data.database.gateway

import jlmd.android.developer.movieswigitest.data.BaseGateway
import jlmd.android.developer.movieswigitest.data.database.daos.MovieDao
import jlmd.android.developer.movieswigitest.data.database.entities.MovieEntity

class OfflineStorageGatewayImpl(
    private val movieDao: MovieDao
): OfflineStorageGateway, BaseGateway() {

    override suspend fun getMoviesLocal() = movieDao.getMoviesLocal()

    override suspend fun saveMovies(movies: List<MovieEntity>) = movieDao.saveMovies(movies)

    override suspend fun getMovieById(movieId: Int) = movieDao.getMovieById(movieId)

    override suspend fun getFavoritesMovies() = movieDao.getFavoritesMovies()

    override suspend fun updateMovie(movie: MovieEntity) = movieDao.updateMovie(movie)
}
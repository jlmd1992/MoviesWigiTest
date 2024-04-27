package jlmd.android.developer.movieswigitest.core

import jlmd.android.developer.movieswigitest.core.model.Movie
import jlmd.android.developer.movieswigitest.data.service.gateway.MoviesGateway
import jlmd.android.developer.movieswigitest.data.database.gateway.OfflineStorageGateway
import jlmd.android.developer.movieswigitest.utils.toMovie
import jlmd.android.developer.movieswigitest.utils.toMovieEntities
import jlmd.android.developer.movieswigitest.utils.toMovieEntity
import jlmd.android.developer.movieswigitest.utils.toMovies
import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.common.collectSuspending
import jlmd.android.developer.movieswigitest.common.thenSuspending
import jlmd.android.developer.movieswigitest.core.model.FavoritesMovies
import jlmd.android.developer.movieswigitest.core.model.UserFavoritesMovies
import jlmd.android.developer.movieswigitest.utils.toMovieData
import jlmd.android.developer.movieswigitest.views.movies.model.MovieDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MoviesRepositoryImpl(
    private val globalCoroutineScope: CoroutineScope,
    private val offlineStorageGateway: OfflineStorageGateway,
    private val moviesGateway: MoviesGateway
): MoviesRepository {

    private val favoritesMoviesStream: MutableStateFlow<FavoritesMovies>

    init {
        favoritesMoviesStream = MutableStateFlow(FavoritesMovies(UserFavoritesMovies.NotFoundFavorites))

        initUserFavoritesMoviesStream()
    }

    override suspend fun getMovies(): RequestResult<List<Movie>> {
        val storedMovies = offlineStorageGateway.getMoviesLocal()
        val hasStoredMovies = storedMovies?.isNotEmpty()

        return if (hasStoredMovies == true) {
            RequestResult.Success(storedMovies.toMovies())
        } else {
            getMoviesFromApi()
                .collectSuspending { movies ->
                    offlineStorageGateway.saveMovies(movies.toMovieEntities())
                }
        }
    }

    private suspend fun getMoviesFromApi(): RequestResult<List<Movie>> {
        return moviesGateway.getMovies()
            .thenSuspending { moviesResponse ->
                val movies = moviesResponse?.moviesList.orEmpty()
                    .distinctBy { movie -> movie.id }
                    .map { it.toMovie() }

                RequestResult.Success(movies)
            }
    }

    override suspend fun getMovieById(movieId: Int): RequestResult<Movie> {
        val movieById = offlineStorageGateway.getMovieById(movieId)
        val movie = movieById.toMovie()
        return RequestResult.Success(movie)
    }

    override suspend fun getFavoritesMovies(): RequestResult<List<Movie>> {
        val movies = offlineStorageGateway.getFavoritesMovies()
        val moviesList = movies.toMovies()
        return RequestResult.Success(moviesList)
    }

    override suspend fun getFavoritesMoviesObserve(): List<Movie> {
        val movies = offlineStorageGateway.getFavoritesMovies()
        return movies.toMovies()
    }

    override suspend fun updateMovie(movie: MovieDetail): MovieDetail {
        val movieEntity = movie.toMovieEntity()
        offlineStorageGateway.updateMovie(movieEntity)
        updateFavoritesList()
        return (offlineStorageGateway.getMovieById(movie.id)).toMovie().toMovieData()
    }

    override fun observeFavoritesMovies(): Flow<FavoritesMovies> =
        favoritesMoviesStream

    private fun initUserFavoritesMoviesStream() {
        globalCoroutineScope.launch {
            val favorites = getFavoritesMoviesObserve()

            val favoritesList = if (favorites.isEmpty()){
                UserFavoritesMovies.NotFoundFavorites
            } else {
                UserFavoritesMovies.MoviesList(favorites)
            }

            favoritesMoviesStream.value = FavoritesMovies(favoritesList)
        }
    }

    private suspend fun updateFavoritesList() {
        val movies = getFavoritesMoviesObserve()
        val favoritesList = if (movies.isEmpty()){
            UserFavoritesMovies.NotFoundFavorites
        } else {
            UserFavoritesMovies.MoviesList(movies)
        }
        favoritesMoviesStream.value = FavoritesMovies(favoritesList)
    }
}
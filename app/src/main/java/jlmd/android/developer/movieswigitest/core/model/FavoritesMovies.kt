package jlmd.android.developer.movieswigitest.core.model

data class FavoritesMovies(
    val userFavorites: UserFavoritesMovies
) {
    fun getList() = (userFavorites as? UserFavoritesMovies.MoviesList)?.let {
        it.movies
    } ?: run { listOf() }
}

sealed class UserFavoritesMovies {

    data class MoviesList(
        val movies: List<Movie>
    ): UserFavoritesMovies()

    data object NotFoundFavorites: UserFavoritesMovies()
}

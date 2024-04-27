package jlmd.android.developer.movieswigitest.views.movies.model

sealed class MoviesViewState{
    data object Loading: MoviesViewState()

    data object NoMoviesFound: MoviesViewState()

    data object Error: MoviesViewState()

    data class ShowMovies(
        val moviesList: List<MovieItem>
    ): MoviesViewState()
}

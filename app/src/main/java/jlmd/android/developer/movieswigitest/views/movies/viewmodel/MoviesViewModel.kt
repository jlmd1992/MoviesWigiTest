package jlmd.android.developer.movieswigitest.views.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jlmd.android.developer.movieswigitest.common.RequestResult
import androidx.lifecycle.viewModelScope
import jlmd.android.developer.movieswigitest.core.model.Movie
import jlmd.android.developer.movieswigitest.core.usecases.GetFavoritesMoviesUseCase
import jlmd.android.developer.movieswigitest.core.usecases.GetMoviesUseCase
import jlmd.android.developer.movieswigitest.core.usecases.ObserveFavoritesMoviesUseCase
import jlmd.android.developer.movieswigitest.views.movies.model.MovieItem
import jlmd.android.developer.movieswigitest.views.movies.model.MoviesViewState
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getFavoritesMoviesUseCase: GetFavoritesMoviesUseCase,
    private val observeFavoritesMoviesUseCase: ObserveFavoritesMoviesUseCase
): ViewModel() {

    private val _moviesListViewState = MutableLiveData<MoviesViewState>()
    val moviesListViewState: LiveData<MoviesViewState> get() = _moviesListViewState

    private val _favoritesMoviesListViewState = MutableLiveData<MoviesViewState>()
    val favoritesMoviesListViewState: LiveData<MoviesViewState> get() = _favoritesMoviesListViewState

    init {
        checkMoviesData()
    }

    private fun checkMoviesData() {
        _moviesListViewState.value = MoviesViewState.Loading

        viewModelScope.launch {
            val movies = getMoviesUseCase.invoke()

            if (movies is RequestResult.Error){
                _moviesListViewState.value = MoviesViewState.Error
            } else {
                if (movies is RequestResult.Success) {
                    val moviesList = movies.data.toViewData()
                    _moviesListViewState.value = MoviesViewState.ShowMovies(moviesList)
                }
            }
        }
    }

    fun getFavoritesMovies(){
        _favoritesMoviesListViewState.value = MoviesViewState.Loading

        viewModelScope.launch {
            val movies = getFavoritesMoviesUseCase.invoke()

            if (movies is RequestResult.Error){
                _favoritesMoviesListViewState.value = MoviesViewState.Error
            } else {
                if (movies is RequestResult.Success) {
                    if (movies.data.isEmpty()){
                        _favoritesMoviesListViewState.value = MoviesViewState.NoMoviesFound
                    } else {
                        val moviesList = movies.data.toViewData()
                        _favoritesMoviesListViewState.value = MoviesViewState.ShowMovies(moviesList)
                    }
                }
            }
        }
    }

    private fun List<Movie>.toViewData(): List<MovieItem> {
        return map {
            MovieItem(
                id = it.id,
                title = it.title,
                description = it.overview,
                image = it.backdropPath
            )
        }
    }

    fun observeFavoritesMovies() {
        _favoritesMoviesListViewState.value = MoviesViewState.Loading

        viewModelScope.launch {
            observeFavoritesMoviesUseCase.invoke()
                .collect{
                    val currentFavorites: List<Movie> = it.getList()

                    if (currentFavorites.isEmpty())
                        _favoritesMoviesListViewState.value = MoviesViewState.NoMoviesFound
                    else
                        _favoritesMoviesListViewState.value = MoviesViewState.ShowMovies(currentFavorites.toViewData())
                }
        }
    }
}
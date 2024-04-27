package jlmd.android.developer.movieswigitest.views.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.core.usecases.GetMovieByIdUseCase
import jlmd.android.developer.movieswigitest.core.usecases.UpdateFavoriteMovieUseCase
import jlmd.android.developer.movieswigitest.utils.toMovieData
import jlmd.android.developer.movieswigitest.views.movies.model.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesDetailViewModel(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val setFavoriteMovieUseCase: UpdateFavoriteMovieUseCase
): ViewModel() {

    private val _movie = MutableLiveData<MovieDetail>()
    val movie: LiveData<MovieDetail> get() = _movie

    private val _movieUpdate = MutableLiveData<MovieDetail>()
    val movieUpdate: LiveData<MovieDetail> get() = _movieUpdate

    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            when (val movie = getMovieByIdUseCase.invoke(movieId)){
                is RequestResult.Success -> _movie.value = movie.data.toMovieData()
                else -> {}
            }
        }
    }

    fun setFavoriteMovie(movie: MovieDetail) {
        viewModelScope.launch(Dispatchers.Default) {
            val result = setFavoriteMovieUseCase.invoke(movie)
            _movieUpdate.postValue(result.data!!)
        }
    }
}
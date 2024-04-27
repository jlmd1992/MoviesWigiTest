package jlmd.android.developer.movieswigitest.views.movies.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import jlmd.android.developer.movieswigitest.databinding.ActivityFavoritesBinding
import jlmd.android.developer.movieswigitest.views.movies.model.MovieItem
import jlmd.android.developer.movieswigitest.views.movies.model.MoviesViewState
import jlmd.android.developer.movieswigitest.views.movies.view.MovieDetailActivity
import jlmd.android.developer.movieswigitest.views.movies.view.MoviesAdapter
import jlmd.android.developer.movieswigitest.views.movies.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesActivity : AppCompatActivity() {

    private val viewModel: MoviesViewModel by viewModel()
    private lateinit var binding: ActivityFavoritesBinding
    private val favoritesAdapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindListeners()
        bindObservers()
        viewModel.observeFavoritesMovies()
    }

    private fun bindObservers() {
        viewModel.favoritesMoviesListViewState.observe(this) { viewState ->
            when(viewState) {
                is MoviesViewState.Loading -> showLoading(true)
                is MoviesViewState.ShowMovies -> showList(viewState.moviesList)
                is MoviesViewState.NoMoviesFound -> showEmptyList()
                is MoviesViewState.Error -> showMessageError()
                else -> {}
            }
        }
    }

    private fun bindListeners() {
        favoritesAdapter.movieListener =
            object : MoviesAdapter.MovieListener {
                override fun onShowMovieDetail(movie: MovieItem) {
                    val intent = Intent(this@FavoritesActivity, MovieDetailActivity::class.java)
                    intent.putExtra("movieId", movie.id)
                    startActivity(intent)
                }
            }

        binding.favoritesMoviesRecycler.adapter = favoritesAdapter
    }

    private fun showEmptyList() {
        showLoading(false)
        binding.noListFavoritesLabel.visibility = View.VISIBLE
        binding.favoritesMoviesRecycler.visibility = View.GONE
    }

    private fun showMessageError(){
        Toast.makeText(this, "An error occurred when consulting the favorites movies", Toast.LENGTH_SHORT).show()
        binding.favoritesMoviesRecycler.visibility = View.GONE
        binding.noListFavoritesLabel.visibility = View.GONE
    }

    private fun showLoading(show: Boolean){
        if (show)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

    private fun showList(moviesList: List<MovieItem>) {
        binding.favoritesMoviesRecycler.visibility = View.VISIBLE
        binding.noListFavoritesLabel.visibility = View.GONE

        favoritesAdapter.items = moviesList

        showLoading(false)
    }
}
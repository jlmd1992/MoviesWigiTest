package jlmd.android.developer.movieswigitest.views.movies.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import jlmd.android.developer.movieswigitest.R
import jlmd.android.developer.movieswigitest.databinding.ActivityMainBinding
import jlmd.android.developer.movieswigitest.views.movies.favorites.FavoritesActivity
import jlmd.android.developer.movieswigitest.views.movies.model.MovieItem
import jlmd.android.developer.movieswigitest.views.movies.model.MoviesViewState
import jlmd.android.developer.movieswigitest.views.movies.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MoviesViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private val adapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindListeners()
        bindObservers()
    }

    private fun bindObservers() {
        viewModel.moviesListViewState.observe(this) { viewState ->
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
        adapter.movieListener =
            object : MoviesAdapter.MovieListener {
                override fun onShowMovieDetail(movie: MovieItem) {
                    val intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
                    intent.putExtra("movieId", movie.id)
                    startActivity(intent)
                }
            }

        binding.moviesRecycler.adapter = adapter
    }

    private fun showEmptyList() {
        showLoading(false)
        binding.noResultsFoundTextView.visibility = View.VISIBLE
        binding.moviesRecycler.visibility = View.GONE
    }

    private fun showMessageError(){
        Toast.makeText(this, "An error occurred when consulting the movies", Toast.LENGTH_SHORT).show()
        binding.moviesRecycler.visibility = View.GONE
        binding.noResultsFoundTextView.visibility = View.GONE
    }

    private fun showLoading(show: Boolean){
        if (show)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

    private fun showList(moviesList: List<MovieItem>) {
        binding.moviesRecycler.visibility = View.VISIBLE
        binding.noResultsFoundTextView.visibility = View.GONE

        adapter.items = moviesList

        showLoading(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_favorites -> {
                showFavoritesList()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showFavoritesList() {
        val intent = Intent(this@MainActivity, FavoritesActivity::class.java)
        startActivity(intent)
    }
}
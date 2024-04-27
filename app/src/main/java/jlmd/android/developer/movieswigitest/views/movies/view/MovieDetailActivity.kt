package jlmd.android.developer.movieswigitest.views.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import jlmd.android.developer.movieswigitest.R
import jlmd.android.developer.movieswigitest.databinding.ActivityMovieDetailBinding
import jlmd.android.developer.movieswigitest.views.movies.model.MovieDetail
import jlmd.android.developer.movieswigitest.views.movies.viewmodel.MoviesDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity() {

    private val viewModel: MoviesDetailViewModel by viewModel()
    private lateinit var binding: ActivityMovieDetailBinding
    private val URL_BASE = "https://image.tmdb.org/t/p/original/"
    private lateinit var movieDetail: MovieDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMovie()
        bindObservers()
        bindListeners()
    }

    private fun getMovie() {
        val movieId = intent.getIntExtra("movieId", 0)
        viewModel.getMovieById(movieId)
    }

    private fun bindObservers() {
        viewModel.movie.observe(this){ movie ->
            showMovie(movie)
        }

        viewModel.movieUpdate.observe(this) {
            showMessage(it)
        }
    }

    private fun showMovie(movie: MovieDetail?) {
        if (movie != null) {
            movieDetail = movie.copy()
            with(binding) {
                titleMovieDetail.text = movie.originalTitle
                releaseDateMovieDetail.text = movie.releaseDate
                overviewMovieDetail.text = movie.overview
                voteAverageMovieDetail.text = movie.voteAverage.toString()
                popularityMovieDetail.text = movie.popularity.toString()
                Glide
                    .with(this@MovieDetailActivity)
                    .load(URL_BASE + movie.posterPath)
                    .into(imageMovieDetail)
                updateIcon(movieDetail)
            }
        } else {
            Toast.makeText(this, "An error occurred when consulting the movie details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindListeners() {
        binding.favoriteMovieBtn.setOnClickListener {
            movieDetail.isFavorite = !movieDetail.isFavorite
            viewModel.setFavoriteMovie(movieDetail)
        }
    }

    private fun showMessage(movieDetail: MovieDetail) {
        Toast.makeText(this, "Movie updated", Toast.LENGTH_SHORT).show()
        updateIcon(movieDetail)
    }

    private fun updateIcon(movieDetail: MovieDetail) {
        if (movieDetail.isFavorite){
            binding.favoriteMovieBtn.setImageDrawable(ContextCompat.getDrawable(this@MovieDetailActivity, R.drawable.ic_star))
        } else {
            binding.favoriteMovieBtn.setImageDrawable(ContextCompat.getDrawable(this@MovieDetailActivity, R.drawable.ic_favorite_border))
        }
    }
}
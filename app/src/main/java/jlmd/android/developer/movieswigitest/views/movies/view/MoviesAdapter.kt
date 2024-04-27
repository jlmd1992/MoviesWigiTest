package jlmd.android.developer.movieswigitest.views.movies.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jlmd.android.developer.movieswigitest.R
import jlmd.android.developer.movieswigitest.databinding.MovieViewItemBinding
import jlmd.android.developer.movieswigitest.views.movies.model.MovieItem

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    var items: List<MovieItem> = listOf()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    var movieListener: MovieListener? = null
    private val URL_BASE = "https://image.tmdb.org/t/p/original/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.movie_view_item, parent, false)

        return MovieViewHolder(view, movieListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = items[position]

        with(holder){
            setListener(movie)
            binding.movieTitle.text = movie.title
            binding.movieOverview.text = movie.description
            Glide
                .with(holder.itemView)
                .load(URL_BASE + movie.image)
                .into(binding.movieImage)
        }
    }

    override fun getItemCount() = items.size

    inner class MovieViewHolder(view: View, movieListener: MovieListener?): RecyclerView.ViewHolder(view){
        val binding = MovieViewItemBinding.bind(view)
        private val listener = movieListener

        fun setListener(movie: MovieItem){
            binding.movieContainer.setOnClickListener {
                this.listener?.onShowMovieDetail(movie)
            }
        }
    }

    interface MovieListener {
        fun onShowMovieDetail(movie: MovieItem)
    }
}
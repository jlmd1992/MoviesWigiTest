package jlmd.android.developer.movieswigitest.views.movies.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val popularity: Double,
    val voteCount: Int,
    val backdropPath: String,
    var isFavorite: Boolean
)

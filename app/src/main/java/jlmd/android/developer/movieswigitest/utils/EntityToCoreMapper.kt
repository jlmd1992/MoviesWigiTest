package jlmd.android.developer.movieswigitest.utils

import jlmd.android.developer.movieswigitest.core.model.Movie
import jlmd.android.developer.movieswigitest.data.database.entities.MovieEntity
import jlmd.android.developer.movieswigitest.views.movies.model.MovieDetail

fun List<MovieEntity>.toMovies() = map { it.toMovie() }

fun MovieEntity.toMovie() =
    Movie(
        id = id,
        title = title,
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        backdropPath = backdropPath,
        isFavorite = isFavorite
    )

fun List<Movie>.toMovieEntities() = map { it.toMovieEntity() }

fun Movie.toMovieEntity() =
   MovieEntity(
        id = id,
        title = title,
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        backdropPath = backdropPath
    )

fun Movie.toMovieData() =
    MovieDetail(
        id = id,
        title = title,
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        backdropPath = backdropPath,
        isFavorite = isFavorite
    )

fun MovieDetail.toMovieEntity() =
    MovieEntity(
        id = id,
        title = title,
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        backdropPath = backdropPath,
        isFavorite = isFavorite
    )
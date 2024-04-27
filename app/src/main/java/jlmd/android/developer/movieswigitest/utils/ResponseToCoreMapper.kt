package jlmd.android.developer.movieswigitest.utils

import jlmd.android.developer.movieswigitest.core.model.Movie
import jlmd.android.developer.movieswigitest.data.service.gateway.dto.MovieDto

fun MovieDto.toMovie() =
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
        backdropPath = backdropPath
    )
package jlmd.android.developer.movieswigitest.core.usecase

import io.mockk.coEvery
import io.mockk.mockk
import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.core.MoviesRepository
import jlmd.android.developer.movieswigitest.core.usecases.UpdateFavoriteMovieUseCase
import jlmd.android.developer.movieswigitest.views.movies.model.MovieDetail
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UpdateFavoriteMovieUseCaseTest {
    @Test
    fun `invoke should return Success after updating movie`() = runBlocking {
        // Arrange
        val movie = MovieDetail(
            id = 1,
            title = "Movie 1",
            originalTitle = "Original Movie 1",
            originalLanguage = "en",
            overview = "Overview movie 1",
            posterPath = "/image.png",
            releaseDate = "2024-01-01",
            voteAverage = 7.0,
            popularity = 211.25,
            voteCount = 4500,
            backdropPath = "/image.png",
            isFavorite = true
        )
        val moviesRepository = mockk<MoviesRepository> {
            coEvery { updateMovie(movie) } returns movie
        }
        val useCase = UpdateFavoriteMovieUseCase(moviesRepository)

        // Act
        val result = useCase.invoke(movie)

        // Assert
        assertEquals(RequestResult.Success(movie), result)
    }
}
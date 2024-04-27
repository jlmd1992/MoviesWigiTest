package jlmd.android.developer.movieswigitest.core.usecase

import io.mockk.coEvery
import io.mockk.mockk
import jlmd.android.developer.movieswigitest.common.DomainError
import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.core.MoviesRepository
import jlmd.android.developer.movieswigitest.core.model.Movie
import jlmd.android.developer.movieswigitest.core.usecases.GetMoviesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.assertEquals

class GetMoviesUseCaseTest {
    @Test
    fun `invoke should return Success when repository returns a list of movies`() = runBlocking {
        // Arrange
        val expectedMovies = listOf(
            Movie(
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
                backdropPath = "/image.png"
            ),
            Movie(
                id = 2,
                title = "Movie 2",
                originalTitle = "Original Movie 2",
                originalLanguage = "en",
                overview = "Overview movie 2",
                posterPath = "/image2.png",
                releaseDate = "2024-02-02",
                voteAverage = 5.0,
                popularity = 234.25,
                voteCount = 5500,
                backdropPath = "/image2.png"
            ),
            Movie(
                id = 3,
                title = "Movie 3",
                originalTitle = "Original Movie 3",
                originalLanguage = "en",
                overview = "Overview movie 3",
                posterPath = "/image3.png",
                releaseDate = "2023-04-03",
                voteAverage = 9.0,
                popularity = 65.25,
                voteCount = 512,
                backdropPath = "/image3.png"
            )
        )
        val moviesRepository = mockk<MoviesRepository> {
            coEvery { getMovies() } returns RequestResult.Success(expectedMovies)
        }
        val getMoviesUseCase = GetMoviesUseCase(moviesRepository)

        val result = getMoviesUseCase.invoke()

        // Assert
        assertEquals(RequestResult.Success(expectedMovies), result)
    }

    @Test
    fun `invoke should return Error when repository returns an error`() = runBlocking {
        val moviesRepository = mockk<MoviesRepository> {
            coEvery { getMovies() } returns RequestResult.Error(DomainError.Server())
        }
        val getMoviesUseCase = GetMoviesUseCase(moviesRepository)

        val result = getMoviesUseCase.invoke()

        // Assert
        assertEquals(RequestResult.Error(DomainError.Server()), result)
    }
}
package jlmd.android.developer.movieswigitest.core.usecase

import io.mockk.coEvery
import io.mockk.mockk
import jlmd.android.developer.movieswigitest.common.DomainError
import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.core.MoviesRepository
import jlmd.android.developer.movieswigitest.core.model.Movie
import jlmd.android.developer.movieswigitest.core.usecases.GetFavoritesMoviesUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetFavoritesMoviesUseCaseTest {
    @Test
    fun `invoke should return Success with favorite movies`() = runBlocking {
        val expectedFavorites = listOf(
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
                backdropPath = "/image.png",
                isFavorite = true
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
                backdropPath = "/image2.png",
                isFavorite = true
            )
        )
        val moviesRepository = mockk<MoviesRepository> {
            coEvery { getFavoritesMovies() } returns RequestResult.Success(expectedFavorites)
        }
        val useCase = GetFavoritesMoviesUseCase(moviesRepository)

        val result = useCase.invoke()

        // Assert
        assertEquals(RequestResult.Success(expectedFavorites), result)
    }

    @Test
    fun `invoke should return Error when repository returns an error`() = runBlocking {
        val moviesRepository = mockk<MoviesRepository> {
            coEvery { getFavoritesMovies() } returns RequestResult.Error(DomainError.NotFound())
        }
        val useCase = GetFavoritesMoviesUseCase(moviesRepository)

        val result = useCase.invoke()

        // Assert
        assertEquals(RequestResult.Error(DomainError.NotFound()), result)
    }
}
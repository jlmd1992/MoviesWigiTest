package jlmd.android.developer.movieswigitest.core.usecase

import io.mockk.coEvery
import io.mockk.mockk
import jlmd.android.developer.movieswigitest.common.DomainError
import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.core.MoviesRepository
import jlmd.android.developer.movieswigitest.core.model.Movie
import jlmd.android.developer.movieswigitest.core.usecases.GetMovieByIdUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetMovieByIdUseCaseTest {
    @Test
    fun `invoke should return Success with movie`() = runBlocking {
        val movieId = 1
        val expectedMovie =
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
            )
        val moviesRepository = mockk<MoviesRepository> {
            coEvery { getMovieById(movieId) } returns RequestResult.Success(expectedMovie)
        }
        val useCase = GetMovieByIdUseCase(moviesRepository)

        val result = useCase.invoke(movieId)

        // Assert
        assertEquals(RequestResult.Success(expectedMovie), result)
    }

    @Test
    fun `invoke should return Error when repository returns an error`() = runBlocking {
        val movieId = 1
        val errorMessage = DomainError.NotFound()
        val moviesRepository = mockk<MoviesRepository> {
            coEvery { getMovieById(movieId) } returns RequestResult.Error(errorMessage)
        }
        val useCase = GetMovieByIdUseCase(moviesRepository)

        val result = useCase.invoke(movieId)

        // Assert
        assertEquals(RequestResult.Error(errorMessage), result)
    }
}
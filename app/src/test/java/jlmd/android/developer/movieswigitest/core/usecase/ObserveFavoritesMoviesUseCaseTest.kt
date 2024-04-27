package jlmd.android.developer.movieswigitest.core.usecase

import io.mockk.coEvery
import io.mockk.mockk
import jlmd.android.developer.movieswigitest.core.MoviesRepository
import jlmd.android.developer.movieswigitest.core.model.FavoritesMovies
import jlmd.android.developer.movieswigitest.core.model.UserFavoritesMovies
import jlmd.android.developer.movieswigitest.core.usecases.ObserveFavoritesMoviesUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class ObserveFavoritesMoviesUseCaseTest {
    @Test
    fun `invoke should emit FavoritesMovies from repository`() = runBlockingTest {
        // Arrange
        val expectedFavorites = FavoritesMovies(UserFavoritesMovies.NotFoundFavorites)
        val moviesRepository = mockk<MoviesRepository> {
            coEvery { observeFavoritesMovies() } returns flow {
                emit(expectedFavorites)
            }
        }
        val useCase = ObserveFavoritesMoviesUseCase(moviesRepository)

        // Act & Assert
        useCase().collect { result ->
            assertEquals(expectedFavorites, result)
        }
    }
}
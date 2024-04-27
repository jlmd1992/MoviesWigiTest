package jlmd.android.developer.movieswigitest.data

import io.mockk.coEvery
import io.mockk.mockk
import jlmd.android.developer.movieswigitest.data.database.daos.MovieDao
import jlmd.android.developer.movieswigitest.data.database.entities.MovieEntity
import jlmd.android.developer.movieswigitest.data.database.gateway.OfflineStorageGatewayImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class OfflineStorageGatewayImplTest {
    @Test
    fun `getMoviesLocal should return movies from DAO`() = runBlocking {
        val expectedMovies = listOf(
            MovieEntity(
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
                isFavorite = false
            ),
            MovieEntity(
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
                isFavorite = false
            ),
            MovieEntity(
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
                backdropPath = "/image3.png",
                isFavorite = false
            )
        )
        val movieDao = mockk<MovieDao> {
            coEvery { getMoviesLocal() } returns expectedMovies
        }
        val gateway = OfflineStorageGatewayImpl(movieDao)

        val result = gateway.getMoviesLocal()

        // Assert
        assertEquals(expectedMovies, result)
    }

    @Test
    fun `saveMovies should call DAO's saveMovies`(): Unit = runBlocking {
        val moviesToSave = listOf(
            MovieEntity(
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
                isFavorite = false
            ),
            MovieEntity(
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
                isFavorite = false
            ),
            MovieEntity(
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
                backdropPath = "/image3.png",
                isFavorite = false
            )
        )
        val movieDao = mockk<MovieDao> {
            coEvery { saveMovies(any()) } returns Unit
        }
        val gateway = OfflineStorageGatewayImpl(movieDao)

        gateway.saveMovies(moviesToSave)

        // Assert
        coEvery { movieDao.saveMovies(moviesToSave) }
    }

    @Test
    fun `getMovieById should return movie from DAO`() = runBlocking {
        val movieId = 1
        val expectedMovie = MovieEntity(
            id = movieId,
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
            isFavorite = false
        )
        val movieDao = mockk<MovieDao> {
            coEvery { getMovieById(movieId) } returns expectedMovie
        }
        val gateway = OfflineStorageGatewayImpl(movieDao)

        val result = gateway.getMovieById(movieId)

        // Assert
        assertEquals(expectedMovie, result)
    }

    @Test
    fun `getFavoritesMovies should return favorites movies from DAO`() = runBlocking {
        val expectedFavorites = listOf(
            MovieEntity(
                id = 1,
                title = "Movie 3",
                originalTitle = "Original Movie 3",
                originalLanguage = "en",
                overview = "Overview movie 3",
                posterPath = "/image3.png",
                releaseDate = "2023-04-03",
                voteAverage = 9.0,
                popularity = 65.25,
                voteCount = 512,
                backdropPath = "/image3.png",
                isFavorite = true
            ),
            MovieEntity(
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
        val movieDao = mockk<MovieDao> {
            coEvery { getFavoritesMovies() } returns expectedFavorites
        }
        val gateway = OfflineStorageGatewayImpl(movieDao)

        val result = gateway.getFavoritesMovies()

        // Assert
        assertEquals(expectedFavorites, result)
    }

    @Test
    fun `updateMovie should call DAO's updateMovie`(): Unit = runBlocking {
        val movieToUpdate = MovieEntity(
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
        val movieDao = mockk<MovieDao> {
            coEvery { updateMovie(movieToUpdate) } returns Unit
        }
        val gateway = OfflineStorageGatewayImpl(movieDao)

        gateway.updateMovie(movieToUpdate)

        // Assert
        coEvery { movieDao.updateMovie(movieToUpdate) }
    }
}
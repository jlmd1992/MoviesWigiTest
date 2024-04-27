package jlmd.android.developer.movieswigitest.core

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import jlmd.android.developer.movieswigitest.common.RequestResult
import jlmd.android.developer.movieswigitest.core.model.Movie
import jlmd.android.developer.movieswigitest.data.database.entities.MovieEntity
import jlmd.android.developer.movieswigitest.data.database.gateway.OfflineStorageGateway
import jlmd.android.developer.movieswigitest.data.service.gateway.MoviesGateway
import jlmd.android.developer.movieswigitest.utils.toMovie
import jlmd.android.developer.movieswigitest.utils.toMovieData
import jlmd.android.developer.movieswigitest.utils.toMovieEntity
import jlmd.android.developer.movieswigitest.utils.toMovies
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MoviesRepositoryImplTest {
    private lateinit var moviesRepository: MoviesRepositoryImpl
    private lateinit var offlineStorageGateway: OfflineStorageGateway
    private lateinit var moviesGateway: MoviesGateway
    private val coroutineScope = CoroutineScope(Dispatchers.Unconfined + Job())

    @Before
    fun setUp() {
        offlineStorageGateway = mockk()
        moviesGateway = mockk()
        moviesRepository = MoviesRepositoryImpl(
            coroutineScope,
            offlineStorageGateway,
            moviesGateway
        )
    }

    @After
    fun tearDown() {
        coroutineScope.cancel()
        clearAllMocks()
    }

    @ExperimentalTime
    @Test
    fun `getMovies should return stored movies if available`() = runBlockingTest {
        // Arrange
        val storedMovies = listOf(
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
        coEvery { offlineStorageGateway.getMoviesLocal() } returns storedMovies

        // Act
        val result = moviesRepository.getMovies()

        // Assert
        assertEquals(RequestResult.Success(storedMovies.toMovies()), result)
    }

    @Test
    fun `getMovieById should return movie by id`() = runBlockingTest {
        val movieId = 1
        val movie = MovieEntity(
            movieId,
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
        )
        coEvery { offlineStorageGateway.getMovieById(movieId) } returns movie

        val result = moviesRepository.getMovieById(movieId)

        // Assert
        assertEquals(RequestResult.Success(movie.toMovie()), result)
    }

    @Test
    fun `getFavoritesMovies should return favorites movies`() = runBlockingTest {
        val favoriteMovies = listOf(
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
                backdropPath = "/image.png"
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
                backdropPath = "/image2.png"
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
                backdropPath = "/image3.png"
            )
        )
        coEvery { offlineStorageGateway.getFavoritesMovies() } returns favoriteMovies

        val result = moviesRepository.getFavoritesMovies()

        // Assert
        assertEquals(RequestResult.Success(favoriteMovies.toMovies()), result)
    }

    @Test
    fun `updateMovie should update movie and update favorites list`() = runBlockingTest {
        // Arrange
        val movie =
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
        coEvery { offlineStorageGateway.updateMovie(any()) } just runs
        coEvery { offlineStorageGateway.getMovieById(movie.id) } returns movie.toMovieEntity()

        // Act
        val movieDetail = movie.toMovieData()
        val result = moviesRepository.updateMovie(movieDetail)

        // Assert
        assertEquals(movieDetail, result)
    }
}
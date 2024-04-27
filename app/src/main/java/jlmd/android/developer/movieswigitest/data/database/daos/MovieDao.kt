package jlmd.android.developer.movieswigitest.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import jlmd.android.developer.movieswigitest.data.database.entities.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table ORDER BY id ASC")
    suspend fun getMoviesLocal(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie_table WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity

    @Query("SELECT * FROM movie_table WHERE isFavorite = 1")
    suspend fun getFavoritesMovies(): List<MovieEntity>

    @Update(entity = MovieEntity::class)
    fun updateMovie(movie: MovieEntity)

}
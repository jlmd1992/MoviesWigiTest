package jlmd.android.developer.movieswigitest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jlmd.android.developer.movieswigitest.data.database.entities.MovieEntity
import jlmd.android.developer.movieswigitest.data.database.daos.MovieDao

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1,
    exportSchema = true
)

abstract class MoviesDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    companion object {
        const val DATABASE_NAME = "MoviesDatabase"

        fun getDatabase(ctx: Context): MoviesDatabase {
            val builder = Room.databaseBuilder(ctx, MoviesDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()

            return builder.build()
        }
    }
}
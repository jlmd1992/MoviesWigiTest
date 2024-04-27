package jlmd.android.developer.movieswigitest.data.di

import android.app.Application
import jlmd.android.developer.movieswigitest.data.database.MoviesDatabase
import jlmd.android.developer.movieswigitest.data.database.gateway.OfflineStorageGateway
import jlmd.android.developer.movieswigitest.data.database.gateway.OfflineStorageGatewayImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application) =
        MoviesDatabase.getDatabase(application)

    fun provideMoviesDao(database: MoviesDatabase) = database.movieDao

    //Database
    single { provideDatabase(androidApplication()) }

    //DAOs
    single { provideMoviesDao(get()) }

    //Gateway
    single<OfflineStorageGateway> {
        OfflineStorageGatewayImpl(get())
    }
}
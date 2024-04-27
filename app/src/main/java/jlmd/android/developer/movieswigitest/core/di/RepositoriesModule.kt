package jlmd.android.developer.movieswigitest.core.di

import jlmd.android.developer.movieswigitest.core.MoviesRepository
import jlmd.android.developer.movieswigitest.core.MoviesRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {

    single<MoviesRepository> { MoviesRepositoryImpl(globalCoroutineScope = get(), moviesGateway = get(), offlineStorageGateway = get()) }

}
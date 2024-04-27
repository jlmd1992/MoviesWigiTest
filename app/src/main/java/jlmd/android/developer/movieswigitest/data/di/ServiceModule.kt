package jlmd.android.developer.movieswigitest.data.di

import jlmd.android.developer.movieswigitest.data.service.MoviesAPI
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {

    single<MoviesAPI> {
        provideService(get(named(RetrofitClients.MOVIES_API.name)))
    }
}

private inline fun <reified T> provideService(retrofit: Retrofit): T = retrofit.create(T::class.java)

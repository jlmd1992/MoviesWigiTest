package jlmd.android.developer.movieswigitest.data.di

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jlmd.android.developer.movieswigitest.data.service.gateway.MoviesGateway
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

enum class RetrofitClients {
    MOVIES_API
}

enum class ConverterFactory {
    MOSHI
}

enum class OkHttpClients {
    DEFAULT
}

enum class Interceptors {
    LOGGING
}

val networkModule = module {

    // Serialization / Deserialization
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single<Converter.Factory>(named(ConverterFactory.MOSHI.name)) { MoshiConverterFactory.create(get()) }

    //region interceptors
    single<Interceptor>(named(Interceptors.LOGGING.name)) { provideHttpLoggingInterceptor() }

    // region Http Clients
    single(named(OkHttpClients.DEFAULT.name)) {
        provideOkHttpClient(
            httpLoggingInterceptor = get(named(Interceptors.LOGGING.name))
        )
    }

    // region retrofit
    single(named(RetrofitClients.MOVIES_API.name)) {
        provideRetrofit(
            url = "https://api.themoviedb.org/3/movie/",
            client = get(named(OkHttpClients.DEFAULT.name)),
            factories = listOf(get(named(ConverterFactory.MOSHI.name)))
        )
    }

    //region Gateway
    single { MoviesGateway(moviesAPI = get()) }
}

fun provideOkHttpClient(httpLoggingInterceptor: Interceptor): OkHttpClient {

    val okHttpClientBuilder = OkHttpClient.Builder()

    okHttpClientBuilder
        .callTimeout(45, TimeUnit.SECONDS)
        .connectTimeout(45, TimeUnit.SECONDS)
        .readTimeout(45, TimeUnit.SECONDS)
        .writeTimeout(45, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)

    return okHttpClientBuilder.build()
}

fun provideRetrofit(
    url: String,
    client: OkHttpClient,
    factories: List<Converter.Factory>
): Retrofit {
    val builder = Retrofit.Builder()
        .baseUrl(url)
        .client(client)

    factories.forEach { builder.addConverterFactory(it) }

    return builder.build()
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor { message -> Log.d("NETWORK", "okhttp: $message") }
}
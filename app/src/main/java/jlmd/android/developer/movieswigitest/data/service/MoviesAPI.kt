package jlmd.android.developer.movieswigitest.data.service

import jlmd.android.developer.movieswigitest.data.service.gateway.dto.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MoviesAPI {

    @GET("popular?api_key=09963e300150f9831c46a1828a82a984&language=en-US")
    suspend fun getMovies(): Response<MoviesResponse>

}
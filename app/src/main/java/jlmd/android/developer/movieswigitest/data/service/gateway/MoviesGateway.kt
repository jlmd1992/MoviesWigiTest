package jlmd.android.developer.movieswigitest.data.service.gateway

import jlmd.android.developer.movieswigitest.data.BaseGateway
import jlmd.android.developer.movieswigitest.data.service.MoviesAPI

class MoviesGateway(
    private val moviesAPI: MoviesAPI
): BaseGateway() {

    suspend fun getMovies() = getResult {
        executeRequest {
            moviesAPI.getMovies()
        }
    }
}
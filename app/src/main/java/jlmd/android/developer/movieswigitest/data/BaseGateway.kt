package jlmd.android.developer.movieswigitest.data

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jlmd.android.developer.movieswigitest.data.service.gateway.ApiResponse
import jlmd.android.developer.movieswigitest.common.DomainError
import jlmd.android.developer.movieswigitest.common.RequestResult
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

open class BaseGateway {

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    protected inline fun <R> getResult(invokeFunction: () -> ApiResponse<R>): RequestResult<R> {
        return try {
            when (val result = invokeFunction()){
                is ApiResponse.Success -> RequestResult.Success(result.response)
                is ApiResponse.Error -> RequestResult.Error(
                    getHttpDomainErrorFromCode(
                        result.httpCode,
                        result.backendCode
                    )
                )
            }
        } catch (ex: Exception){
            Log.e("ERROR_GATEWAY", "Repository error while getting data ${ex.message}")
            RequestResult.Error(getDomainError(ex))
        }
    }

    protected inline fun <T> executeRequest(function: () -> Response<T>): ApiResponse<T?> {
        val response = function.invoke()
        return if (response.isSuccessful) ApiResponse.Success(response.body())
        else ApiResponse.Error(response.code(), getErrorCode(response))
    }

    protected fun <T> getErrorCode(response: Response<T>) =
        response.errorBody()?.string()?.let { json ->
            val fromJson = moshi.adapter(AppError::class.java).fromJson(json)
            fromJson?.code ?: fromJson?.statusCode
        }

    protected fun getDomainError(ex: Exception): DomainError {
        return when (ex) {
            is IOException -> getIODomainError(ex)
            is HttpException -> getHttpDomainError(ex)
            else -> DomainError.UnknownData()
        }
    }

    private fun getIODomainError(ex: IOException): DomainError {
        return when (ex) {
            is UnknownHostException -> DomainError.Network()
            else -> DomainError.UnknownData()
        }
    }

    private fun getHttpDomainError(ex: HttpException) = getHttpDomainErrorFromCode(ex.code())

    fun getHttpDomainErrorFromCode(code: Int, backendCode: Int? = null) =
        when (code) {
            400 -> DomainError.BadRequest(backendCode = backendCode)
            404 -> DomainError.NotFound(backendCode = backendCode)
            in 500..600 -> DomainError.Server(backendCode = backendCode)
            else -> DomainError.UnknownData(backendCode = backendCode)
        }
}
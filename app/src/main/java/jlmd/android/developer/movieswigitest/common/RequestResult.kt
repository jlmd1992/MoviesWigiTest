package jlmd.android.developer.movieswigitest.common

sealed class RequestResult<out R>{

    data class Success<out T>(
        val data: T
    ): RequestResult<T>()

    data class Error(
        val error: DomainError
    ): RequestResult<Nothing>()

    fun isSuccess() = this is Success
}

suspend fun <R, T> RequestResult<R>.thenSuspending(
    block: suspend (R) -> RequestResult<T>
): RequestResult<T> {
    return when (this) {
        is RequestResult.Error -> this
        is RequestResult.Success -> block(this.data)
    }
}

suspend fun <R> RequestResult<R>.collectSuspending(
    block: suspend (R) -> Unit
): RequestResult<R> {
    return when (this) {
        is RequestResult.Error -> this
        is RequestResult.Success -> {
            block(this.data)
            this
        }
    }
}

sealed class DomainError(
    open val contentParams: String,
    val data: DomainErrorType
) {
    //Network
    data class Server(
        override val contentParams: String = "errors/server",
        val backendCode: Int? = null
    ): DomainError(contentParams, DomainErrorType.DATA)

    data class Network(
        override val contentParams: String = "errors/network",
    ): DomainError(contentParams, DomainErrorType.DATA)

    data class NotFound(
        override val contentParams: String = "errors/notFound",
        val backendCode: Int? = null
    ): DomainError(contentParams, DomainErrorType.DATA)

    data class BadRequest(
        override val contentParams: String = "errors/badRequest",
        val backendCode: Int? = null
    ): DomainError(contentParams, DomainErrorType.DATA)

    data class UnknownData(
        override val contentParams: String = "errors/generalError",
        val backendCode: Int? = null
    ): DomainError(contentParams, DomainErrorType.DATA)
}

enum class DomainErrorType {
    DATA
}
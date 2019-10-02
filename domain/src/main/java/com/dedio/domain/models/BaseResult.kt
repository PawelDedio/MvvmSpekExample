package com.dedio.domain.models

/**
 * Wrapper class for passing data between 'data', 'presentation' and 'domain' modules
 */
@Suppress("unused")
sealed class BaseResult<out T : Any> {

    class Ok<out T : Any>(val value: T) : BaseResult<T>()
    class CancelledError : Error()
    class ApiError(val code: Int, exception: Exception? = null) : Error(exception)
    class NetworkError(exception: Exception? = null) : Error(exception)
    open class Error(val exception: Exception? = null) : BaseResult<Nothing>()

    protected var isErrorHandled = false

    inline fun whenOk(block: Ok<T>.() -> Unit): BaseResult<T> {
        if (this is Ok) {
            this.block()
        }
        return this
    }

    inline fun whenNetworkError(block: NetworkError.() -> Unit): BaseResult<T> {
        if (this is NetworkError && !isErrorHandled) {
            this.block()
            isErrorHandled = true
        }
        return this
    }

    inline fun onNetworkError(block: () -> Unit): BaseResult<T> {
        if (this is NetworkError && !isErrorHandled) {
            block()
            isErrorHandled = true
        }
        return this
    }

    inline fun whenApiError(block: ApiError.() -> Unit): BaseResult<T> {
        if (this is ApiError && !isErrorHandled) {
            block()
            isErrorHandled = true
        }
        return this
    }

    inline fun onApiError(block: () -> Unit): BaseResult<T> {
        if (this is ApiError && !isErrorHandled) {
            block()
            isErrorHandled = true
        }
        return this
    }

    inline fun whenError(block: Error.() -> Unit): BaseResult<T> {
        if (this is Error && this !is CancelledError && !isErrorHandled) {
            this.block()
            isErrorHandled = true
        }
        return this
    }

    inline fun onError(block: () -> Unit): BaseResult<T> {
        if (this is Error && this !is CancelledError && !isErrorHandled) {
            block()
            isErrorHandled = true
        }
        return this
    }
}

class CombinedResult<T : Any>(private vararg val results: BaseResult<T>) {
    fun isOk() = results.count { it is BaseResult.Ok } == results.size
    fun firstErrorResult() = results.first { it is BaseResult.Error } as BaseResult.Error
}

inline fun <T : Any, reified R : Any> BaseResult<T>.map(block: BaseResult.Ok<T>.() -> BaseResult<R>): BaseResult<R> = if (this is BaseResult.Ok) {
    this.block()
} else {
    this as BaseResult.Error
    castErrorResult(this)
}

inline fun <reified T : Any> castErrorResult(source: BaseResult.Error): BaseResult<T> {
    return when (source) {
        is BaseResult.ApiError -> {
            BaseResult.ApiError(source.code, source.exception)
        }
        is BaseResult.CancelledError -> {
            BaseResult.CancelledError()
        }
        is BaseResult.NetworkError -> {
            BaseResult.NetworkError(source.exception)
        }
        is BaseResult.Error -> {
            BaseResult.Error(source.exception)
        }
        else -> {
            throw IllegalArgumentException("Incorrect type")
        }
    }
}
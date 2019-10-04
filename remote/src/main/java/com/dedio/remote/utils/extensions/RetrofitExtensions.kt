package com.dedio.remote.utils.extensions

import com.dedio.domain.models.BaseResult
import retrofit2.Response
import java.io.IOException

fun <T : Any> Response<T>.convertToBaseResult(): BaseResult<T> = if (isSuccessful) {
    val body = body() ?: throw IOException("Body can't be null")

    BaseResult.Ok(body)
} else {
    BaseResult.ApiError(code())
}
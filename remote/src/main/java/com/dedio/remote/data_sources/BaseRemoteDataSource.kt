package com.dedio.remote.data_sources

import com.dedio.domain.models.BaseResult
import com.dedio.remote.utils.extensions.convertToBaseResult
import retrofit2.Response
import java.lang.Exception
import java.net.UnknownHostException

abstract class BaseRemoteDataSource {

    inline protected fun <T : Any> safeDoNetworkCall(action: () -> Response<T>): BaseResult<T> {
        return try {
            val response = action()
            response.convertToBaseResult()
        } catch (e: UnknownHostException) {
            BaseResult.NetworkError(e)
        } catch (e: Exception) {
            BaseResult.Error(e)
        }
    }
}
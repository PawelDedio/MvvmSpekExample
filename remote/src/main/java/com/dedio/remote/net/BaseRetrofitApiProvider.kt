package com.dedio.remote.net

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseRetrofitApiProvider<A>(endpoint: String, timeout: Long, apiClass: Class<A>, gson: Gson) {

    private val api: A

    init {
        val gsonConverter: GsonConverterFactory = GsonConverterFactory.create(gson)
        api = Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(gsonConverter)
            .client(buildClient(timeout))
            .build()
            .create(apiClass)
    }

    private fun buildClient(timeout: Long): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .readTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)

        val networkInterceptor = createNetworkInterceptor()
        if (networkInterceptor != null) {
            builder.addNetworkInterceptor(networkInterceptor)
        }

        builder.addInterceptor(loggingInterceptor)
        builder.addNetworkInterceptor(loggingInterceptor)

        return builder.build()
    }

    protected open fun createNetworkInterceptor(): Interceptor? = null

    fun getApi(): A = api
}
package com.dedio.cache.utils

import androidx.room.TypeConverter
import com.dedio.domain.models.RepositoryCommitsResponse
import com.google.gson.Gson

class RepositoryCommitsListTypeConverter {

    private val gson: Gson = Gson()

    @TypeConverter
    fun jsonStringAsObject(json: String): RepositoryCommitsResponse {
        return gson.fromJson(json, RepositoryCommitsResponse::class.java)
    }

    @TypeConverter
    fun objectAsJsonString(model: RepositoryCommitsResponse): String {
        return gson.toJson(model)
    }
}
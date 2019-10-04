package com.dedio.cache.utils

import androidx.room.TypeConverter
import com.dedio.domain.models.RepositoryListResponse
import com.google.gson.Gson

class RepositoryListTypeConverter {
    
    private val gson: Gson = Gson()

    @TypeConverter
    fun jsonStringAsObject(json: String): RepositoryListResponse {
        return gson.fromJson(json, RepositoryListResponse::class.java)
    }

    @TypeConverter
    fun objectAsJsonString(model: RepositoryListResponse): String {
        return gson.toJson(model)
    }
}
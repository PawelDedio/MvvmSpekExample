package com.dedio.data.utils

import com.dedio.domain.utils.SerializationHelper
import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject
import kotlin.reflect.KClass

class SerializationHelperImpl @Inject constructor(private val gson: Gson) : SerializationHelper {

    override fun toJson(model: Any): String = gson.toJson(model)

    override fun <T : Any> fromJson(json: String, type: KClass<T>): T = gson.fromJson(json, type.java)

    override fun <T : Any> fromJson(json: String, typeOf: Type): T = gson.fromJson(json, typeOf)
}
package com.dedio.domain.utils

import java.lang.reflect.Type
import kotlin.reflect.KClass

interface SerializationHelper {

    fun toJson(model: Any): String

    fun <T : Any> fromJson(json: String, type: KClass<T>): T

    fun <T : Any> fromJson(json: String, typeOf: Type): T
}
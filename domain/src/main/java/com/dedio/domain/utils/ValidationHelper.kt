package com.dedio.domain.utils

interface ValidationHelper {

    fun checkIsNotEmpty(reference: String?): Boolean

    fun checkIsNotEmpty(reference: Collection<*>?): Boolean

    fun checkIsNotEmpty(obj: Any?): Boolean
}
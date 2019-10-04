package com.dedio.data.utils

import com.dedio.domain.utils.ValidationHelper
import javax.inject.Inject

class ValidationHelperImpl @Inject constructor() : ValidationHelper {

    override fun checkIsNotEmpty(reference: String?): Boolean {
        return !reference.isNullOrEmpty()
    }

    override fun checkIsNotEmpty(reference: Collection<*>?): Boolean {
        return reference != null && !reference.isEmpty()
    }

    override fun checkIsNotEmpty(obj: Any?): Boolean {
        return obj != null
    }
}
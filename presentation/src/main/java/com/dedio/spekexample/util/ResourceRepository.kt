package com.dedio.spekexample.util

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceRepository @Inject constructor(private val context: Context) {

    fun getString(@StringRes resId: Int) = context.getString(resId)
}
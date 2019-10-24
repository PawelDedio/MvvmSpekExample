package com.treeline.cleantemplate.presentation.testeUtil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> LiveData<T>.asMutable(): MutableLiveData<T> {
    return this as MutableLiveData<T>
}
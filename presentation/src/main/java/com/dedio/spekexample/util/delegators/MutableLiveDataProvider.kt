package com.dedio.spekexample.util.delegators

import androidx.lifecycle.MutableLiveData
import kotlin.reflect.KProperty

class MutableLiveDataProvider<T>(private val mutableLiveData: MutableLiveData<T> = MutableLiveData()) {

    operator fun getValue(thisRef: Any, property: KProperty<*>) = mutableLiveData
}
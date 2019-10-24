package com.dedio.spekexample.util.delegators

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import kotlin.reflect.KProperty

class MutableLiveDataProvider<T>(private val mutableLiveData: MutableLiveData<T> = mock()) {

    private val cachedValue: MutableLiveData<T> = mock()

    operator fun getValue(thisRef: Any, property: KProperty<*>) = cachedValue
}
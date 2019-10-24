package com.dedio.spekexample.util.delegators

import com.dedio.spekexample.util.LiveEvent
import com.nhaarman.mockitokotlin2.mock
import kotlin.reflect.KProperty

class LiveEventProvider<T>(private val liveEvent: LiveEvent<T> = mock()) {

    private val cachedValue: LiveEvent<T> = mock()

    operator fun getValue(thisRef: Any, property: KProperty<*>) = cachedValue
}
package com.treeline.cleantemplate.presentation.util.delegators

import com.nhaarman.mockitokotlin2.mock
import com.treeline.cleantemplate.presentation.util.LiveEvent
import kotlin.reflect.KProperty

class LiveEventProvider<T>(private val liveEvent: LiveEvent<T> = mock()) {

    private val cachedValue: LiveEvent<T> = mock()

    operator fun getValue(thisRef: Any, property: KProperty<*>) = cachedValue
}
package com.dedio.spekexample.util.delegators

import com.dedio.spekexample.util.LiveEvent
import kotlin.reflect.KProperty

class LiveEventProvider<T>(private val liveEvent: LiveEvent<T> = LiveEvent()) {

    operator fun getValue(thisRef: Any, property: KProperty<*>) = liveEvent
}
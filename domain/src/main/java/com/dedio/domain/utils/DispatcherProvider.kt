package com.dedio.domain.utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun main(): CoroutineDispatcher
    fun computation(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
}
package com.dedio.data.utils

import com.dedio.domain.utils.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {

    override fun main() = Dispatchers.Main

    override fun computation() = Dispatchers.Default

    override fun io() = Dispatchers.IO
}
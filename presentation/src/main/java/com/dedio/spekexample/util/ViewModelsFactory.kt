package com.dedio.spekexample.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dedio.spekexample.base.BaseViewModel
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
        private val viewModelsMap: Map<Class<out BaseViewModel>, @JvmSuppressWildcards Provider<BaseViewModel>>) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val castedModelClass = modelClass as Class<out BaseViewModel>

        val creator = viewModelsMap[castedModelClass] ?:
        viewModelsMap.asIterable().firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("Wrong model class $modelClass")

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
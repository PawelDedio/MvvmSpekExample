package com.dedio.spekexample.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.util.ResourceRepository
import com.dedio.spekexample.util.delegators.LiveEventProvider
import com.dedio.spekexample.util.delegators.MutableLiveDataProvider

abstract class BaseViewModel(application: MainApplication, private val resourceRepository: ResourceRepository) : AndroidViewModel(application) {

    protected val _isLoading by MutableLiveDataProvider<Boolean>()
    val isLoading = _isLoading as LiveData<Boolean>

    val hideKeyboardAction by LiveEventProvider<Unit?>()

    fun getResString(id: Int) = resourceRepository.getString(id)

    override fun onCleared() {
        super.onCleared()

        _isLoading.postValue(false)
    }

    protected fun showLoading(isCalledFromMainThread: Boolean = true) {
        safeCallIsLoading(true, isCalledFromMainThread)
    }

    protected fun hideLoading(isCalledFromMainThread: Boolean = true) {
        safeCallIsLoading(false, isCalledFromMainThread)
    }

    private fun safeCallIsLoading(value: Boolean, isCalledFromMainThread: Boolean) {
        if(isCalledFromMainThread) {
            _isLoading.value = value
        } else {
            _isLoading.postValue(value)
        }
    }

    protected fun hideKeyboard() {
        hideKeyboardAction.postValue(null)
    }
}
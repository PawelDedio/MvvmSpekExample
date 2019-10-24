package com.dedio.spekexample.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dedio.domain.models.BaseResult
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.R
import com.dedio.spekexample.util.LiveEvent
import com.dedio.spekexample.util.ResourceRepository
import com.dedio.spekexample.util.delegators.LiveEventProvider
import com.dedio.spekexample.util.delegators.MutableLiveDataProvider

abstract class BaseViewModel(application: MainApplication, private val resourceRepository: ResourceRepository) : AndroidViewModel(application) {

    val isLoading by MutableLiveDataProvider<Boolean>()

    val hideKeyboardAction by LiveEventProvider<Unit?>()

    fun getResString(id: Int) = resourceRepository.getString(id)

    override fun onCleared() {
        super.onCleared()

        isLoading.postValue(false)
    }

    protected fun showLoading(isCalledFromMainThread: Boolean = true) {
        safeCallIsLoading(true, isCalledFromMainThread)
    }

    protected fun hideLoading(isCalledFromMainThread: Boolean = true) {
        safeCallIsLoading(false, isCalledFromMainThread)
    }

    private fun safeCallIsLoading(value: Boolean, isCalledFromMainThread: Boolean) {
        if(isCalledFromMainThread) {
            isLoading.value = value
        } else {
            isLoading.postValue(value)
        }
    }

    protected fun hideKeyboard() {
        hideKeyboardAction.postValue(null)
    }

    protected fun actionForNoInternet(errorMessage: LiveEvent<String>): ((error: BaseResult.NetworkError) -> Unit) = {
        val error = resourceRepository.getString(R.string.error_no_connection)
        errorMessage.postValue(error)
    }
}
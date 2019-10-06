package com.dedio.spekexample.name_input

import com.dedio.domain.utils.ValidationHelper
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.R
import com.dedio.spekexample.base.BaseViewModel
import com.dedio.spekexample.util.ResourceRepository
import com.dedio.spekexample.util.delegators.LiveEventProvider
import com.dedio.spekexample.util.delegators.MutableLiveDataProvider
import javax.inject.Inject

class NameInputViewModel @Inject constructor(application: MainApplication,
                                             private val resourceRepository: ResourceRepository,
                                             private val validationHelper: ValidationHelper) :
        BaseViewModel(application, resourceRepository) {

    val userName by MutableLiveDataProvider<String>()
    val userNameError by MutableLiveDataProvider<String>()

    val navigateToRepositoriesAction by LiveEventProvider<String>()

    fun onSearchClick() {
        clearErrors()

        if(!validateAndShowError()) {
            return
        }

        navigateToRepositoriesAction.postValue(userName.value!!)
    }

    private fun clearErrors() {
        userNameError.postValue(null)
    }

    private fun validateAndShowError() = if(validationHelper.checkIsNotEmpty(userName.value)) {
        true
    } else {
        val error = resourceRepository.getString(R.string.name_input_error_empty_name)
        userNameError.postValue(error)

        false
    }
}
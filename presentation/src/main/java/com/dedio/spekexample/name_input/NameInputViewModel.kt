package com.dedio.spekexample.name_input

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dedio.domain.use_cases.GetRepositoriesUseCase
import com.dedio.domain.utils.ValidationHelper
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.R
import com.dedio.spekexample.base.BaseViewModel
import com.dedio.spekexample.util.ResourceRepository
import com.dedio.spekexample.util.delegators.MutableLiveDataProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class NameInputViewModel @Inject constructor(application: MainApplication,
                                             private val resourceRepository: ResourceRepository,
                                             private val getRepositoriesUseCase: GetRepositoriesUseCase,
                                             private val validationHelper: ValidationHelper) :
        BaseViewModel(application, resourceRepository) {

    val userName by MutableLiveDataProvider<String>()

    private val _userNameError by MutableLiveDataProvider<String>()
    val userNameError = _userNameError as LiveData<String>

    fun onSearchClick() {
        clearErrors()

        if(!validateAndShowError()) {
            return
        }

        viewModelScope.launch {
            if (userName.value != null) {
                val params = GetRepositoriesUseCase.Params(userName.value!!)
                showLoading()

                getRepositoriesUseCase.execute(params).whenOk {
                    Log.e("NameInputViewModel", "success")
                }.whenError {
                    val error = resourceRepository.getString(R.string.name_input_error_wrong_name)
                    _userNameError.postValue(error)
                }

                hideLoading()
            }
        }
    }

    private fun clearErrors() {
        _userNameError.postValue(null)
    }

    private fun validateAndShowError() = if(validationHelper.checkIsNotEmpty(userName.value)) {
        true
    } else {
        val error = resourceRepository.getString(R.string.name_input_error_empty_name)
        _userNameError.postValue(error)

        false
    }
}
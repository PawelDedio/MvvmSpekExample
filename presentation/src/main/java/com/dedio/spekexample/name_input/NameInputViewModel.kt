package com.dedio.spekexample.name_input

import androidx.lifecycle.viewModelScope
import com.dedio.domain.use_cases.GetRepositoriesUseCase
import com.dedio.domain.utils.ValidationHelper
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.R
import com.dedio.spekexample.base.BaseViewModel
import com.dedio.spekexample.models.UserRepositoriesUiModel
import com.dedio.spekexample.models.toUiModel
import com.dedio.spekexample.util.ResourceRepository
import com.dedio.spekexample.util.delegators.LiveEventProvider
import com.dedio.spekexample.util.delegators.MutableLiveDataProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class NameInputViewModel @Inject constructor(application: MainApplication,
                                             private val resourceRepository: ResourceRepository,
                                             private val getRepositoriesUseCase: GetRepositoriesUseCase,
                                             private val validationHelper: ValidationHelper) :
        BaseViewModel(application, resourceRepository) {

    val userName by MutableLiveDataProvider<String>()
    val userNameError by MutableLiveDataProvider<String>()

    val navigateToRepositoriesAction by LiveEventProvider<UserRepositoriesUiModel>()

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
                    val model = this.value.toUiModel(userName.value!!)
                    navigateToRepositoriesAction.postValue(model)
                }.whenError {
                    val error = resourceRepository.getString(R.string.name_input_error_wrong_name)
                    userNameError.postValue(error)
                }

                hideLoading()
            }
        }
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
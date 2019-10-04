package com.dedio.spekexample.name_input

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dedio.domain.use_cases.GetRepositoriesUseCase
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.base.BaseViewModel
import com.dedio.spekexample.util.ResourceRepository
import com.dedio.spekexample.util.delegators.MutableLiveDataProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class NameInputViewModel @Inject constructor(application: MainApplication,
                                             resourceRepository: ResourceRepository,
                                             private val getRepositoriesUseCase: GetRepositoriesUseCase) :
        BaseViewModel(application, resourceRepository) {

    val userName by MutableLiveDataProvider<String>()

    fun onSearchClick() {
        viewModelScope.launch {
            if (userName.value != null) {
                val params = GetRepositoriesUseCase.Params(userName.value!!)
                showLoading()

                getRepositoriesUseCase.execute(params).whenOk {
                    Log.e("NameInputViewModel", "success")
                }.whenError {
                    Log.e("NameInputViewModel", "error")
                }

                hideLoading()
            }
        }
    }
}
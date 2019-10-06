package com.dedio.spekexample.user_repositories

import androidx.lifecycle.viewModelScope
import com.dedio.domain.use_cases.GetRepositoriesUseCase
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.R
import com.dedio.spekexample.base.BaseViewModel
import com.dedio.spekexample.models.UserRepositoriesUiModel
import com.dedio.spekexample.models.toUiModel
import com.dedio.spekexample.util.ResourceRepository
import com.dedio.spekexample.util.delegators.MutableLiveDataProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepositoriesViewModel @Inject constructor(application: MainApplication,
                                                    private val resourceRepository: ResourceRepository,
                                                    private val getRepositoriesUseCase: GetRepositoriesUseCase) : BaseViewModel(application, resourceRepository) {

    val userName by MutableLiveDataProvider<String>()
    val userRepositories by MutableLiveDataProvider<UserRepositoriesUiModel>()
    val errorMessage by MutableLiveDataProvider<String>()

    init {
        loadRepositories()
    }

    fun onRefresh() {
        loadRepositories(true)
    }

    private fun loadRepositories(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            if (userName.value != null) {
                val params = GetRepositoriesUseCase.Params(userName.value!!, forceRefresh)
                showLoading()

                getRepositoriesUseCase.execute(params).whenOk {
                    val model = this.value.toUiModel(userName.value!!)
                    userRepositories.postValue(model)
                }.whenError {
                    val error = resourceRepository.getString(R.string.name_input_error_wrong_name)
                    errorMessage.postValue(error)
                }

                hideLoading()
            }
        }
    }
}
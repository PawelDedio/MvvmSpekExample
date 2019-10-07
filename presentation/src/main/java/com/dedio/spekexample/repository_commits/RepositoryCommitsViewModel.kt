package com.dedio.spekexample.repository_commits

import androidx.lifecycle.viewModelScope
import com.dedio.domain.use_cases.GetRepositoryCommitsUseCase
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.R
import com.dedio.spekexample.base.BaseViewModel
import com.dedio.spekexample.models.RepositoryCommitsUiModel
import com.dedio.spekexample.models.UserRepositoryUiModel
import com.dedio.spekexample.models.toUiModel
import com.dedio.spekexample.util.ResourceRepository
import com.dedio.spekexample.util.delegators.LiveEventProvider
import com.dedio.spekexample.util.delegators.MutableLiveDataProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryCommitsViewModel @Inject constructor(application: MainApplication,
                                                     private val resourceRepository: ResourceRepository,
                                                     private val getRepositoryCommitsUseCase: GetRepositoryCommitsUseCase) :
        BaseViewModel(application, resourceRepository) {

    val repository by MutableLiveDataProvider<UserRepositoryUiModel>()
    val commits by MutableLiveDataProvider<RepositoryCommitsUiModel>()

    val errorMessage by LiveEventProvider<String>()

    init {
        loadCommits()
    }

    fun onRefresh() {
        loadCommits(true)
    }

    private fun loadCommits(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            if (repository.value != null) {
                val repository = repository.value!!
                val params = GetRepositoryCommitsUseCase.Params(repository.userName, repository.name, forceRefresh)
                showLoading()

                getRepositoryCommitsUseCase.execute(params).whenOk {
                    val model = value.toUiModel()
                    commits.postValue(model)
                }.whenNetworkError(actionForNoInternet(errorMessage)).whenApiError {
                    val error = resourceRepository.getString(R.string.user_repositories_loading_error)
                    errorMessage.postValue(error)
                }

                hideLoading()
            }
        }
    }
}

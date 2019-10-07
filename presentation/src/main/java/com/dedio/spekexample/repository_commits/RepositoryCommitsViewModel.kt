package com.dedio.spekexample.repository_commits

import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.base.BaseViewModel
import com.dedio.spekexample.models.RepositoryCommitsUiModel
import com.dedio.spekexample.models.UserRepositoryUiModel
import com.dedio.spekexample.util.ResourceRepository
import com.dedio.spekexample.util.delegators.LiveEventProvider
import com.dedio.spekexample.util.delegators.MutableLiveDataProvider
import javax.inject.Inject

class RepositoryCommitsViewModel @Inject constructor(application: MainApplication,
                                                     private val resourceRepository: ResourceRepository) :
        BaseViewModel(application, resourceRepository) {

    val repository by MutableLiveDataProvider<UserRepositoryUiModel>()
    val commits by MutableLiveDataProvider<RepositoryCommitsUiModel>()

    val errorMessage by LiveEventProvider<String>()
}

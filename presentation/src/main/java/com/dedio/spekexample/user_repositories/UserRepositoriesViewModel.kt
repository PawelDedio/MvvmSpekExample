package com.dedio.spekexample.user_repositories

import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.base.BaseViewModel
import com.dedio.spekexample.models.UserRepositoriesUiModel
import com.dedio.spekexample.util.ResourceRepository
import com.dedio.spekexample.util.delegators.MutableLiveDataProvider
import javax.inject.Inject

class UserRepositoriesViewModel @Inject constructor(application: MainApplication,
                                                    resourceRepository: ResourceRepository) : BaseViewModel(application, resourceRepository) {

    val repositories by MutableLiveDataProvider<UserRepositoriesUiModel>()
}
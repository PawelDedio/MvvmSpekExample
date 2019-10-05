package com.dedio.spekexample.util

import com.dedio.domain.models.RepositoryListResponse
import com.dedio.spekexample.base.BaseActivity
import javax.inject.Inject

class NavigationService @Inject constructor(activity: BaseActivity) {

    fun navigateToRepositoriesScreen(userName: String, repositories: RepositoryListResponse) {
        //TODO: Implementation for that
    }
}
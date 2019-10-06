package com.dedio.spekexample.user_repositories

import com.dedio.spekexample.R
import com.dedio.spekexample.models.UserRepositoryUiModel
import com.dedio.spekexample.views.data_binding_lists.DataBindingAdapter
import javax.inject.Inject

class RepositoriesAdapter @Inject constructor(diffItemCallback: UserRepositoriesDiffItemCallback) :
        DataBindingAdapter<UserRepositoryUiModel>(diffItemCallback) {

    override fun provideLayoutResForViewType(position: Int) = R.layout.cell_user_repositories
}
package com.dedio.spekexample.repository_commits

import com.dedio.spekexample.R
import com.dedio.spekexample.models.RepositoryCommitUiModel
import com.dedio.spekexample.views.data_binding_lists.DataBindingAdapter
import javax.inject.Inject

class RepositoryCommitsAdapter @Inject constructor(diffItemCallback: RepositoryCommitsDiffItemCallback) : DataBindingAdapter<RepositoryCommitUiModel>(diffItemCallback) {

    override fun provideLayoutResForViewType(position: Int) = R.layout.cell_repository_commits
}
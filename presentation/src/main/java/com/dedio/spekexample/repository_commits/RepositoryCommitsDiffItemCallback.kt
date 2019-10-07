package com.dedio.spekexample.repository_commits

import androidx.recyclerview.widget.DiffUtil
import com.dedio.spekexample.models.RepositoryCommitUiModel
import javax.inject.Inject

class RepositoryCommitsDiffItemCallback @Inject constructor() :
        DiffUtil.ItemCallback<RepositoryCommitUiModel>() {

    override fun areItemsTheSame(oldItem: RepositoryCommitUiModel,
                                 newItem: RepositoryCommitUiModel) = oldItem.sha == newItem.sha

    override fun areContentsTheSame(oldItem: RepositoryCommitUiModel,
                                    newItem: RepositoryCommitUiModel) = oldItem == newItem
}
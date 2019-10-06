package com.dedio.spekexample.user_repositories

import androidx.recyclerview.widget.DiffUtil
import com.dedio.spekexample.models.UserRepositoryUiModel

class UserRepositoriesDiffItemCallback : DiffUtil.ItemCallback<UserRepositoryUiModel>() {

    override fun areItemsTheSame(oldItem: UserRepositoryUiModel,
                                 newItem: UserRepositoryUiModel) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UserRepositoryUiModel,
                                    newItem: UserRepositoryUiModel) = oldItem == newItem
}
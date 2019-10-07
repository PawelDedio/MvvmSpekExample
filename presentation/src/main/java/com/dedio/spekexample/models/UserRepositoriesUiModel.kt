package com.dedio.spekexample.models

import com.dedio.domain.models.RepositoryListResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRepositoriesUiModel(val userName: String, val repositories: List<UserRepositoryUiModel>) : BaseUiModel()

fun RepositoryListResponse.toUiModel(userName: String): UserRepositoriesUiModel {
    val mappedList = this.map { it.toUiModel(userName) }

    return UserRepositoriesUiModel(userName, mappedList)
}
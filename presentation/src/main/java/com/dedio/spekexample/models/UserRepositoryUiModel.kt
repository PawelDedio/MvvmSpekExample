package com.dedio.spekexample.models

import com.dedio.domain.models.RepositoryListItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRepositoryUiModel(val userName: String, val id: String, val nodeId: String, val name: String, val description: String?) : BaseUiModel()

fun RepositoryListItem.toUiModel(userName: String) = UserRepositoryUiModel(userName, id.toString(), nodeId, name, description)
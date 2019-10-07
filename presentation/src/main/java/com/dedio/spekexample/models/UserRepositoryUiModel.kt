package com.dedio.spekexample.models

import com.dedio.domain.models.RepositoryListItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRepositoryUiModel(val id: String, val nodeId: String, val name: String, val description: String?) : BaseUiModel()

fun RepositoryListItem.toUiModel() = UserRepositoryUiModel(id.toString(), nodeId, name, description)
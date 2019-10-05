package com.dedio.spekexample.models

import com.dedio.domain.models.RepositoryListItem
import com.dedio.domain.models.RepositoryListResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRepositoryUiModel(val id: String, val noteId: String?, val name: String, val description: String?) : BaseUiModel()

fun RepositoryListItem.toUiModel() = UserRepositoryUiModel(id.toString(), noteId, name, description)
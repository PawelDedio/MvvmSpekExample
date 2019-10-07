package com.dedio.spekexample.models

import com.dedio.domain.models.RepositoryCommitsResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryCommitsUiModel(val repositories: List<RepositoryCommitUiModel>) : BaseUiModel()

fun RepositoryCommitsResponse.toUiModel(): RepositoryCommitsUiModel {
    val mappedList = map { it.toUiModel() }
   return RepositoryCommitsUiModel(mappedList)
}
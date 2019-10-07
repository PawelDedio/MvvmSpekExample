package com.dedio.spekexample.models

import com.dedio.domain.models.RepositoryCommitsItem
import kotlinx.android.parcel.Parcelize

@Parcelize
class RepositoryCommitUiModel(val sha: String, val message: String) : BaseUiModel()

fun RepositoryCommitsItem.toUiModel() = RepositoryCommitUiModel(sha, commit.message)
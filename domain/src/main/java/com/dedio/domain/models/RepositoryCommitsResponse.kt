package com.dedio.domain.models

import com.google.gson.annotations.SerializedName

class RepositoryCommitsResponse : ArrayList<RepositoryCommitsItem>()

data class RepositoryCommitsItem(@SerializedName("sha") val sha: String, @SerializedName(
        "commit") val commit: RepositoryCommitsCommit)

data class RepositoryCommitsCommit(@SerializedName("message") val message: String)
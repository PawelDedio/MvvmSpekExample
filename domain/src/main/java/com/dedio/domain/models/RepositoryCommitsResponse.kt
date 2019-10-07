package com.dedio.domain.models

import com.google.gson.annotations.SerializedName

class RepositoryCommitsResponse : ArrayList<RepositoryCommitsItem>()

data class RepositoryCommitsItem(@SerializedName("sha") val sha: String, @SerializedName(
        "message") val message: String)
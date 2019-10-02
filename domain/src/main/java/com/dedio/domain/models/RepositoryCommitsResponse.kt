package com.dedio.domain.models

class RepositoryCommitsResponse : ArrayList<RepositoryCommitsItem>()

data class RepositoryCommitsItem(val sha: String, val message: String)
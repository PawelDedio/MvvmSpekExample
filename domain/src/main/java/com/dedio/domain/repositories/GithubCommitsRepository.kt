package com.dedio.domain.repositories

import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryCommitsResponse
import com.dedio.domain.models.RepositoryListResponse

interface GithubCommitsRepository {

    suspend fun getRepositoryCommits(userName: String, repositoryName: String, forceRefresh: Boolean): BaseResult<RepositoryCommitsResponse>
}
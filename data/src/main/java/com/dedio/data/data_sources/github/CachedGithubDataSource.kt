package com.dedio.data.data_sources.github

import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryCommitsResponse
import com.dedio.domain.models.RepositoryListResponse

interface CachedGithubDataSource {
    suspend fun getRepositories(userName: String): BaseResult<RepositoryListResponse>
    fun setRepositories(userName: String, response: RepositoryListResponse)

    suspend fun getRepositoryCommits(userName: String, repositoryName: String): BaseResult<RepositoryCommitsResponse>
    fun setRepositoryCommits(userName: String, repositoryName: String, response: RepositoryCommitsResponse)
}
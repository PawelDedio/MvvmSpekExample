package com.dedio.data.data_sources.github

import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryCommitsResponse
import com.dedio.domain.models.RepositoryListResponse

interface RemoteGithubDataSource {
    suspend fun getRepositories(userName: String): BaseResult<RepositoryListResponse>
    suspend fun getRepositoryCommits(userName: String, repositoryName: String): BaseResult<RepositoryCommitsResponse>
}
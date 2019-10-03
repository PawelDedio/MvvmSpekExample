package com.dedio.data.data_sources.github

import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListResponse

interface CachedGithubDataSource {
    suspend fun getRepositories(userName: String): BaseResult<RepositoryListResponse>
    fun setRepositories(userName: String, response: RepositoryListResponse)
}
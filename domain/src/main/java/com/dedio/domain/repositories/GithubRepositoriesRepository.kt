package com.dedio.domain.repositories

import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListResponse

interface GithubRepositoriesRepository {
    suspend fun getRepositories(userName: String, forceRefresh: Boolean): BaseResult<RepositoryListResponse>
}
package com.dedio.cache.data_sources.github

import com.dedio.data.data_sources.github.CachedGithubDataSource
import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListResponse
import javax.inject.Inject

class CachedGithubDataSourceImpl @Inject constructor() : CachedGithubDataSource {

    override suspend fun getRepositories(userName: String): BaseResult<RepositoryListResponse> {
    }

    override fun setRepositories(userName: String, response: RepositoryListResponse) {
    }
}
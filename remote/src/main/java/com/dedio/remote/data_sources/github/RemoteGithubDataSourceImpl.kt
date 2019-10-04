package com.dedio.remote.data_sources.github

import com.dedio.data.data_sources.github.RemoteGithubDataSource
import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListResponse
import javax.inject.Inject

class RemoteGithubDataSourceImpl @Inject constructor() : RemoteGithubDataSource {

    override suspend fun getRepositories(userName: String): BaseResult<RepositoryListResponse> {
        return BaseResult.Error()
    }
}
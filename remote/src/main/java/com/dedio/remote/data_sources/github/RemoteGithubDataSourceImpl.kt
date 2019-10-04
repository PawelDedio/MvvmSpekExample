package com.dedio.remote.data_sources.github

import com.dedio.data.data_sources.github.RemoteGithubDataSource
import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListResponse
import com.dedio.remote.net.GithubApi
import com.dedio.remote.utils.extensions.convertToBaseResult
import javax.inject.Inject

class RemoteGithubDataSourceImpl @Inject constructor(private val githubApi: GithubApi) : RemoteGithubDataSource {

    override suspend fun getRepositories(userName: String): BaseResult<RepositoryListResponse> {
        val response = githubApi.getUserRepos(userName)

        return response.convertToBaseResult()
    }
}
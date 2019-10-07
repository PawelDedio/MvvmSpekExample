package com.dedio.data.repositories

import com.dedio.data.data_sources.github.CachedGithubDataSource
import com.dedio.data.data_sources.github.RemoteGithubDataSource
import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListResponse
import com.dedio.domain.repositories.GithubRepositoriesRepository
import javax.inject.Inject

class GithubRepositoriesRepositoryImpl @Inject constructor(
        private val cachedSource: CachedGithubDataSource,
        private val remoteSource: RemoteGithubDataSource) : GithubRepositoriesRepository {

    override suspend fun getRepositories(userName: String,
                                         forceRefresh: Boolean): BaseResult<RepositoryListResponse> {
        if(forceRefresh) {
            return remoteSource.getRepositories(userName).whenOk {
                cachedSource.setRepositories(userName, value)
            }
        } else {
            return cachedSource.getRepositories(userName).whenError {
                return getRepositories(userName,true)
            }
        }
    }
}
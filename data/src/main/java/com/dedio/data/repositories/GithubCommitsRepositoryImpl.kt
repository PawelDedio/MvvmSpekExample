package com.dedio.data.repositories

import com.dedio.data.data_sources.github.CachedGithubDataSource
import com.dedio.data.data_sources.github.RemoteGithubDataSource
import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryCommitsResponse
import com.dedio.domain.repositories.GithubCommitsRepository
import javax.inject.Inject

class GithubCommitsRepositoryImpl @Inject constructor(
        private val cachedSource: CachedGithubDataSource,
        private val remoteSource: RemoteGithubDataSource): GithubCommitsRepository {

    override suspend fun getRepositoryCommits(userName: String, repositoryName: String,
                                              forceRefresh: Boolean): BaseResult<RepositoryCommitsResponse> {

        if(forceRefresh) {
            return remoteSource.getRepositoryCommits(userName, repositoryName).whenOk {
                cachedSource.setRepositoryCommits(userName, repositoryName, value)
            }
        } else {
            return cachedSource.getRepositoryCommits(userName, repositoryName).whenError {
                return getRepositoryCommits(userName, repositoryName, true)
            }
        }
    }
}
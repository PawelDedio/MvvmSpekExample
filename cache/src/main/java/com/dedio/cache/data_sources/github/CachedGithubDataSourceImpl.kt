package com.dedio.cache.data_sources.github

import com.dedio.cache.daos.GithubRepositoriesDao
import com.dedio.cache.entities.GithubRepositories
import com.dedio.data.data_sources.github.CachedGithubDataSource
import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListResponse
import javax.inject.Inject

class CachedGithubDataSourceImpl @Inject constructor(private val githubRepositoriesDao: GithubRepositoriesDao) : CachedGithubDataSource {

    override suspend fun getRepositories(userName: String): BaseResult<RepositoryListResponse> {
        val result = githubRepositoriesDao.loadByUserName(userName)

        return if(result == null) {
            BaseResult.Error()
        } else {
            BaseResult.Ok(result.repositoryList)
        }
    }

    override fun setRepositories(userName: String, response: RepositoryListResponse) {
        githubRepositoriesDao.insert(GithubRepositories(userName, response))
    }
}
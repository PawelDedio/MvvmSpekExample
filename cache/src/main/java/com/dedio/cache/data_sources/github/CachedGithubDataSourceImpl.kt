package com.dedio.cache.data_sources.github

import com.dedio.cache.daos.GithubRepositoriesDao
import com.dedio.cache.daos.GithubRepositoryCommitsDao
import com.dedio.cache.entities.GithubRepositories
import com.dedio.cache.entities.GithubRepositoryCommits
import com.dedio.data.data_sources.github.CachedGithubDataSource
import com.dedio.data.exceptions.CacheException
import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryCommitsResponse
import com.dedio.domain.models.RepositoryListResponse
import javax.inject.Inject

class CachedGithubDataSourceImpl @Inject constructor(private val githubRepositoriesDao: GithubRepositoriesDao,
                                                     private val githubRepositoryCommitsDao: GithubRepositoryCommitsDao) : CachedGithubDataSource {

    override suspend fun getRepositories(userName: String): BaseResult<RepositoryListResponse> {
        val result = githubRepositoriesDao.loadByUserName(userName)

        return if(result == null) {
            BaseResult.Error(CacheException())
        } else {
            BaseResult.Ok(result.repositoryList)
        }
    }

    override fun setRepositories(userName: String, response: RepositoryListResponse) {
        githubRepositoriesDao.insert(GithubRepositories(userName, response))
    }

    override suspend fun getRepositoryCommits(userName: String,
                                              repositoryName: String): BaseResult<RepositoryCommitsResponse> {
        val result = githubRepositoryCommitsDao.loadById(userName, repositoryName)

        return if(result == null) {
            BaseResult.Error(CacheException())
        } else {
            BaseResult.Ok(result.commitsList)
        }
    }

    override fun setRepositoryCommits(userName: String, repositoryName: String,
                                      response: RepositoryCommitsResponse) {
        githubRepositoryCommitsDao.insert(GithubRepositoryCommits(userName, repositoryName, response))
    }
}
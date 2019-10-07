package com.dedio.domain.use_cases

import com.dedio.domain.models.RepositoryCommitsResponse
import com.dedio.domain.repositories.GithubCommitsRepository
import com.dedio.domain.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRepositoryCommitsUseCase @Inject constructor(
        private val githubCommitsRepository: GithubCommitsRepository,
        private val dispatcherProvider: DispatcherProvider) :
        BaseUseCase<RepositoryCommitsResponse, GetRepositoryCommitsUseCase.Params>() {

    override suspend fun execute(params: Params) = withContext(dispatcherProvider.io()) {
        githubCommitsRepository.getRepositoryCommits(params.userName, params.repositoryName,
                params.forceRefresh)
    }

    data class Params(val userName: String, val repositoryName: String,
                      val forceRefresh: Boolean = false)

}
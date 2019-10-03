package com.dedio.domain.use_cases

import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListResponse
import com.dedio.domain.repositories.GithubRepositoriesRepository
import com.dedio.domain.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRepositoriesUseCase @Inject constructor(private val repositoriesRepository: GithubRepositoriesRepository,
                                                 private val dispatcherProvider: DispatcherProvider) : BaseUseCase<RepositoryListResponse, GetRepositoriesUseCase.Params>() {

    override suspend fun execute(params: Params): BaseResult<RepositoryListResponse> = withContext(dispatcherProvider.io()) {
        repositoriesRepository.getRepositories(params.userName, params.forceRefresh)
    }

    data class Params(val userName: String, val forceRefresh: Boolean)
}
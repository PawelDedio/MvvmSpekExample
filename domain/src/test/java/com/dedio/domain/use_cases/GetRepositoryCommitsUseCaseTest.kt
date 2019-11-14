package com.dedio.domain.use_cases

import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryCommitsResponse
import com.dedio.domain.repositories.GithubCommitsRepository
import com.dedio.domain.utils.DispatcherProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ExperimentalCoroutinesApi
class GetRepositoryCommitsUseCaseTest : Spek({
    val commitsRepository by memoized { mock<GithubCommitsRepository>() }
    val dispatcherProvider by memoized { mock<DispatcherProvider>() }

    val subject by memoized { GetRepositoryCommitsUseCase(commitsRepository, dispatcherProvider) }

    beforeEachTest {
        whenever(dispatcherProvider.io()).thenReturn(TestCoroutineDispatcher())
    }

    describe("execute") {
        suspend fun callMethod(userName: String = "name", repositoryName: String = "repository", forceRefresh: Boolean = true): BaseResult<RepositoryCommitsResponse> {
            val params = GetRepositoryCommitsUseCase.Params(userName, repositoryName, forceRefresh)

            return subject.execute(params)
        }

        it("should call repository method with correct params") {
            val userName = "test"
            val repositoryName = "testRepository"
            val forceRefresh = false

            runBlocking {
                callMethod(userName, repositoryName, forceRefresh)
                verify(commitsRepository).getRepositoryCommits(userName, repositoryName, forceRefresh)
            }
        }
    }
})
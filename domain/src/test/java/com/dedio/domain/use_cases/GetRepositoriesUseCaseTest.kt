package com.dedio.domain.use_cases

import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListResponse
import com.dedio.domain.repositories.GithubRepositoriesRepository
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
class GetRepositoriesUseCaseTest : Spek({
    val repositoriesRepository by memoized { mock<GithubRepositoriesRepository>() }
    val dispatcherProvider by memoized { mock<DispatcherProvider>() }

    val subject by memoized { GetRepositoriesUseCase(repositoriesRepository, dispatcherProvider) }

    beforeEachTest {
        whenever(dispatcherProvider.io()).thenReturn(TestCoroutineDispatcher())
    }

    describe("execute") {
        suspend fun callMethod(userName: String = "testName", forceRefresh: Boolean = true): BaseResult<RepositoryListResponse> {
            val params = GetRepositoriesUseCase.Params(userName, forceRefresh)

            return subject.execute(params)
        }

        it("should call repository method with correct params") {
            val userName = "test"
            val forceRefresh = false

            runBlocking {
                callMethod(userName, forceRefresh)
                verify(repositoriesRepository).getRepositories(userName, forceRefresh)
            }
        }
    }
})
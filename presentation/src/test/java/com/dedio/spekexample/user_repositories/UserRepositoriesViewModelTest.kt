package com.dedio.spekexample.user_repositories

import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListItem
import com.dedio.domain.models.RepositoryListResponse
import com.dedio.domain.use_cases.GetRepositoriesUseCase
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.test_utils.asMutable
import com.dedio.spekexample.util.ResourceRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ExperimentalCoroutinesApi
class UserRepositoriesViewModelTest : Spek({

    val application by memoized { mock<MainApplication>() }
    val resourcesRepository by memoized { mock<ResourceRepository>() }
    val getRepositoriesUseCase by memoized { mock<GetRepositoriesUseCase>() }

    val subject by memoized {
        UserRepositoriesViewModel(application, resourcesRepository, getRepositoriesUseCase)
    }

    val repositoryResponse by memoized {
        RepositoryListItem(0, "0", "Test Repository", "Test repository description")
    }
    val repositoryListResponse by memoized {
        val response = RepositoryListResponse()
        response.add(repositoryResponse)
        response
    }

    beforeEachTest {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    describe("onRefresh()") {
        fun callMethod(userName: String = "testName") {
            whenever(subject.userName.value).thenReturn(userName)

            subject.onRefresh()
        }

        it("should show loading") {
            callMethod()

            verify(subject.isLoading.asMutable()).value = true
        }

        it("should call use case with correct params") {
            val userName = "userName"
            val requiredParams = GetRepositoriesUseCase.Params(userName, true)

            callMethod(userName)

            runBlocking {
                verify(getRepositoriesUseCase).execute(requiredParams)
            }
        }

        context("loading success") {
            beforeEachTest {
                runBlocking {
                    whenever(getRepositoriesUseCase.execute(any())).thenReturn(BaseResult.Ok(repositoryListResponse))
                }

                callMethod()
            }

            it("should set value on UI") {
                verify(subject.userRepositories).postValue(any())
            }
        }

        context("loading failure") {

        }
    }
})
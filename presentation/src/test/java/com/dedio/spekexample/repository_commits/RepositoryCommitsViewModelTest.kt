package com.dedio.spekexample.repository_commits

import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryCommitsCommit
import com.dedio.domain.models.RepositoryCommitsItem
import com.dedio.domain.models.RepositoryCommitsResponse
import com.dedio.domain.use_cases.GetRepositoryCommitsUseCase
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.R
import com.dedio.spekexample.models.UserRepositoryUiModel
import com.dedio.spekexample.util.ResourceRepository
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.mockito.Mockito
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ExperimentalCoroutinesApi
class RepositoryCommitsViewModelTest : Spek({

    val application by memoized { mock<MainApplication>() }
    val resourcesRepository by memoized { mock<ResourceRepository>() }
    val getRepositoryCommitsUseCase by memoized { mock<GetRepositoryCommitsUseCase>() }

    val subject by memoized { RepositoryCommitsViewModel(application, resourcesRepository, getRepositoryCommitsUseCase) }

    val repository by memoized { UserRepositoryUiModel("testUserName", "testId", "testNoteId", "testName", "testDescription") }

    val commit by memoized { RepositoryCommitsCommit("testMessage") }
    val commitItem by memoized { RepositoryCommitsItem("testSha", commit) }
    val repositoryCommitsResponse by memoized {
        val response = RepositoryCommitsResponse()
        response.add(commitItem)
        response
    }

    val dispatcher = TestCoroutineDispatcher()

    beforeEachTest {
        Dispatchers.setMain(dispatcher)
    }

    describe("init") {
        fun callMethod(): RepositoryCommitsViewModel {
            Mockito.clearInvocations(getRepositoryCommitsUseCase)

            dispatcher.pauseDispatcher()
            val subject = RepositoryCommitsViewModel(application, resourcesRepository,
                getRepositoryCommitsUseCase)
            whenever(subject.repository.value).thenReturn(repository)
            dispatcher.resumeDispatcher()

            return subject
        }

        it("should call use case method with correct params") {
            val params = GetRepositoryCommitsUseCase.Params(repository.userName, repository.name, false)

            callMethod()

            runBlocking {
                verify(getRepositoryCommitsUseCase).execute(params)
            }
        }
    }

    describe("onRefresh()") {
        fun callMethod() {
            whenever(subject.repository.value).thenReturn(repository)
            subject.onRefresh()
        }

        it("should show loading before use case call") {
            val inOrder = inOrder(subject.isLoading, getRepositoryCommitsUseCase)

            callMethod()

            inOrder.verify(subject.isLoading).value = true
            runBlocking {
                inOrder.verify(getRepositoryCommitsUseCase).execute(any())
            }
        }

        it("should call use case method with correct params") {
            val params = GetRepositoryCommitsUseCase.Params(repository.userName, repository.name, true)

            callMethod()

            runBlocking {
                verify(getRepositoryCommitsUseCase).execute(params)
            }
        }

        it("should hide loading after use case call") {
            val inOrder = inOrder(subject.isLoading, getRepositoryCommitsUseCase)

            callMethod()

            runBlocking {
                inOrder.verify(getRepositoryCommitsUseCase).execute(any())
            }
            inOrder.verify(subject.isLoading).value = false
        }

        context("loading success") {
            beforeEachTest {
                runBlocking {
                    whenever(getRepositoryCommitsUseCase.execute(any())).thenReturn(
                        BaseResult.Ok(repositoryCommitsResponse))
                }

                callMethod()
            }

            it("should set value on UI") {
                verify(subject.commits).postValue(any())
            }
        }

        context("loading failure") {
            val errorMessage = "Error"

            context("no internet error") {
                beforeEachTest {
                    runBlocking {
                        whenever(getRepositoryCommitsUseCase.execute(any())).thenReturn(BaseResult.NetworkError())
                    }

                    whenever(resourcesRepository.getString(R.string.error_no_connection)).thenReturn(errorMessage)

                    callMethod()
                }

                it("should show error message") {
                    verify(subject.errorMessage).postValue(errorMessage)
                }
            }

            context("api error") {
                beforeEachTest {
                    runBlocking {
                        whenever(getRepositoryCommitsUseCase.execute(any())).thenReturn(BaseResult.ApiError(400))
                    }

                    whenever(resourcesRepository.getString(R.string.user_repositories_loading_error)).thenReturn(errorMessage)

                    callMethod()
                }

                it("should show error message") {
                    verify(subject.errorMessage).postValue(errorMessage)
                }
            }
        }
    }
})
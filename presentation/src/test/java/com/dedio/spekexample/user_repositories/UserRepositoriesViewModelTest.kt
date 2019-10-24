package com.dedio.spekexample.user_repositories

import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListItem
import com.dedio.domain.models.RepositoryListResponse
import com.dedio.domain.use_cases.GetRepositoriesUseCase
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.R
import com.dedio.spekexample.models.UserRepositoryUiModel
import com.dedio.spekexample.models.toUiModel
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

    val dispatcher = TestCoroutineDispatcher()

    beforeEachTest {
        Dispatchers.setMain(dispatcher)
    }

    describe("init") {
        fun callMethod(userName: String = "testName"): UserRepositoriesViewModel {
            Mockito.clearInvocations(getRepositoriesUseCase)

            dispatcher.pauseDispatcher()
            val subject = UserRepositoriesViewModel(application, resourcesRepository,
                    getRepositoriesUseCase)
            whenever(subject.userName.value).thenReturn(userName)
            dispatcher.resumeDispatcher()

            return subject
        }

        it("should call use case method with correct params") {
            val userName = "userName"
            val requiredParams = GetRepositoriesUseCase.Params(userName, false)

            callMethod(userName)

            runBlocking {
                verify(getRepositoriesUseCase).execute(requiredParams)
            }
        }
    }

    describe("onRefresh()") {
        fun callMethod(userName: String = "testName") {
            whenever(subject.userName.value).thenReturn(userName)

            subject.onRefresh()
        }

        it("should show loading before use case call") {
            val inOrder = inOrder(subject.isLoading, getRepositoriesUseCase)

            callMethod()

            inOrder.verify(subject.isLoading).value = true
            runBlocking {
                inOrder.verify(getRepositoriesUseCase).execute(any())
            }
        }

        it("should call use case with correct params") {
            val userName = "userName"
            val requiredParams = GetRepositoriesUseCase.Params(userName, true)

            callMethod(userName)

            runBlocking {
                verify(getRepositoriesUseCase).execute(requiredParams)
            }
        }

        it("should hide loading after use case call") {
            val inOrder = inOrder(subject.isLoading, getRepositoriesUseCase)

            callMethod()

            runBlocking {
                inOrder.verify(getRepositoriesUseCase).execute(any())
            }
            inOrder.verify(subject.isLoading).value = false
        }

        context("loading success") {
            beforeEachTest {
                runBlocking {
                    whenever(getRepositoriesUseCase.execute(any())).thenReturn(
                            BaseResult.Ok(repositoryListResponse))
                }

                callMethod()
            }

            it("should set value on UI") {
                verify(subject.userRepositories).postValue(any())
            }
        }

        context("loading failure") {
            val errorMessage = "Error"

            context("no internet error") {
                beforeEachTest {
                    runBlocking {
                        whenever(getRepositoriesUseCase.execute(any())).thenReturn(BaseResult.NetworkError())
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
                        whenever(getRepositoriesUseCase.execute(any())).thenReturn(BaseResult.ApiError(400))
                    }

                    whenever(resourcesRepository.getString(R.string.name_input_error_wrong_name)).thenReturn(errorMessage)

                    callMethod()
                }

                it("should show error message") {
                    verify(subject.errorMessage).postValue(errorMessage)
                }
            }
        }
    }

    describe("onRepositoryClicked()") {
        val model = repositoryResponse.toUiModel("testUser")

        fun callMethod(uiModel: UserRepositoryUiModel = model) {
            subject.onRepositoryClicked(uiModel)
        }

        it("should navigate to commits screen") {
            callMethod()

            verify(subject.navigateToCommitsAction).postValue(model)
        }
    }
})
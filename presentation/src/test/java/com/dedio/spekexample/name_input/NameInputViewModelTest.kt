package com.dedio.spekexample.name_input

import com.dedio.domain.utils.ValidationHelper
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.R
import com.dedio.spekexample.util.ResourceRepository
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ExperimentalCoroutinesApi
class NameInputViewModelTest : Spek({

    val application by memoized { mock<MainApplication>() }
    val resourcesRepository by memoized { mock<ResourceRepository>() }
    val validationHelper by memoized { mock<ValidationHelper>() }

    val subject by memoized {
        NameInputViewModel(application, resourcesRepository, validationHelper)
    }

    beforeEachTest {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    describe("onSearchClick()") {

        fun callMethod() = subject.onSearchClick()

        it("should hide keyboard") {
            callMethod()

            verify(subject.hideKeyboardAction, times(1)).postValue(null)
        }

        it("should clear errors") {
            callMethod()

            verify(subject.userNameError, atLeastOnce()).postValue(null)
        }

        context("validation success") {
            val userName = "testName"
            val userNameError = "Wrong name"

            beforeEachTest {
                whenever(subject.userName.value).thenReturn(userName)
                whenever(validationHelper.checkIsNotEmpty(userName)).thenReturn(true)
                whenever(resourcesRepository.getString(R.string.name_input_error_empty_name)).thenReturn(
                    userNameError
                )

                callMethod()
            }

            it("should not show error") {
                verify(subject.userNameError, never()).postValue(userNameError)
            }

            it("should navigate to repositories screen") {
                verify(subject.navigateToRepositoriesAction).postValue(userName)
            }
        }

        context("validation failure") {
            val userName = ""
            val userNameError = "Wrong name"

            beforeEachTest {
                whenever(subject.userName.value).thenReturn(userName)
                whenever(validationHelper.checkIsNotEmpty(userName)).thenReturn(false)
                whenever(resourcesRepository.getString(R.string.name_input_error_empty_name)).thenReturn(
                    userNameError
                )

                callMethod()
            }

            it("should show error") {
                verify(subject.userNameError).postValue(userNameError)
            }

            it("should not navigate to repositories screen") {
                verify(subject.navigateToRepositoriesAction, never()).postValue(userName)
            }
        }
    }
})
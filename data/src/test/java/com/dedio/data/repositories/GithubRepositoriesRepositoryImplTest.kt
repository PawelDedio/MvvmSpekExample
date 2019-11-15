package com.dedio.data.repositories

import com.dedio.data.data_sources.github.CachedGithubDataSource
import com.dedio.data.data_sources.github.RemoteGithubDataSource
import com.dedio.domain.models.BaseResult
import com.dedio.domain.models.RepositoryListItem
import com.dedio.domain.models.RepositoryListResponse
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class GithubRepositoriesRepositoryImplTest : Spek({
    val cachedSource by memoized { mock<CachedGithubDataSource>() }
    val remoteSource by memoized { mock<RemoteGithubDataSource>() }

    val subject by memoized { GithubRepositoriesRepositoryImpl(cachedSource, remoteSource) }

    val repositoryListItem by memoized {
        RepositoryListItem(0, "testId", "testName", "testDescription")
    }
    val repositoryList by memoized {
        val list = RepositoryListResponse()
        list.add(repositoryListItem)
        list
    }

    describe("getRepositories()") {
        fun callMethod(userName: String,
                               forceRefresh: Boolean): BaseResult<RepositoryListResponse> {
            return runBlocking { subject.getRepositories(userName, forceRefresh) }
        }

        context("forceRefresh is true") {
            fun callMethod(userName: String): BaseResult<RepositoryListResponse> {
                return callMethod(userName, true)
            }

            context("remoteSource is correct") {
                val expectedResult = BaseResult.Ok(repositoryList)
                val userName = "testName"
                beforeEachTest {
                    runBlocking {
                        whenever(remoteSource.getRepositories(userName)).thenReturn(expectedResult)
                    }
                }

                it("should return value from remote source") {
                    val result = callMethod(userName)
                    assertEquals(expectedResult, result)
                }

                it("should save repositories into cache") {
                    val result = callMethod(userName)
                    runBlocking { verify(cachedSource).setRepositories(userName, (result as BaseResult.Ok).value) }
                }
            }

            context("remote source returns error") {
                val expectedResult = BaseResult.Error()
                val userName = "testName"
                beforeEachTest {
                    runBlocking {
                        whenever(remoteSource.getRepositories(userName)).thenReturn(expectedResult)
                    }
                }

                it("should return value from remote source") {
                    val result = callMethod(userName)
                    assertEquals(expectedResult, result)
                }

                it("should not update cached repositories") {
                    callMethod(userName)

                    verify(cachedSource, never()).setRepositories(any(), any())
                }
            }
        }

        context("forceRefresh is false") {
            fun callMethod(userName: String): BaseResult<RepositoryListResponse> {
                return callMethod(userName, false)
            }

            context("cachedSource is correct") {
                val expectedResult = BaseResult.Ok(repositoryList)
                val userName = "testName"

                beforeEachTest {
                    runBlocking {
                        whenever(cachedSource.getRepositories(any())).thenReturn(expectedResult)
                    }
                }

                it("should return the cached value") {
                    val result = callMethod(userName)

                    assertEquals(expectedResult, result)
                }
            }

            context("cachedSource returns error") {
                val remoteResult = BaseResult.Ok(repositoryList)
                val userName = "testName"

                beforeEachTest {
                    runBlocking {
                        whenever(remoteSource.getRepositories(userName)).thenReturn(remoteResult)
                        whenever(cachedSource.getRepositories(userName)).thenReturn(BaseResult.Error())
                    }
                }

                it("should return the remote value") {
                    val result = callMethod(userName)

                    assertEquals(remoteResult, result)
                }
            }
        }
    }
})
package com.dedio.data.repositories

import com.dedio.data.data_sources.github.CachedGithubDataSource
import com.dedio.data.data_sources.github.RemoteGithubDataSource
import com.dedio.domain.models.*
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class GithubCommitsRepositoryImplTest : Spek({
    val cachedSource by memoized { mock<CachedGithubDataSource>() }
    val remoteSource by memoized { mock<RemoteGithubDataSource>() }

    val subject by memoized { GithubCommitsRepositoryImpl(cachedSource, remoteSource) }

    val commit by memoized { RepositoryCommitsCommit("Test commit message") }
    val commitListItem by memoized { RepositoryCommitsItem("testSha", commit) }
    val commitsList by memoized {
        val list = RepositoryCommitsResponse()
        list.add(commitListItem)
        list
    }

    describe("getRepositoryCommits()") {
        fun callMethod(userName: String, repositoryName: String,
                       forceRefresh: Boolean) = runBlocking {
            subject.getRepositoryCommits(userName, repositoryName, forceRefresh)
        }

        context("forceRefresh is true") {
            fun callMethod(userName: String, repositoryName: String): BaseResult<RepositoryCommitsResponse> {
                return callMethod(userName, repositoryName, true)
            }

            context("remoteSource is correct") {
                val expectedResult = BaseResult.Ok(commitsList)
                val userName = "testName"
                val repositoryName = "testRepository"

                beforeEachTest {
                    runBlocking {
                        whenever(remoteSource.getRepositoryCommits(userName, repositoryName)).thenReturn(expectedResult)
                    }
                }

                it("should return value from remote source") {
                    val result = callMethod(userName, repositoryName)
                    Assert.assertEquals(expectedResult, result)
                }

                it("should save repositories into cache") {
                    val result = callMethod(userName, repositoryName)
                    runBlocking {
                        verify(cachedSource).setRepositoryCommits(userName, repositoryName,
                                (result as BaseResult.Ok).value)
                    }
                }
            }

            context("remote source returns error") {
                val expectedResult = BaseResult.Error()
                val userName = "testName"
                val repositoryName = "testRepository"

                beforeEachTest {
                    runBlocking {
                        whenever(remoteSource.getRepositoryCommits(userName, repositoryName)).thenReturn(expectedResult)
                    }
                }

                it("should return value from remote source") {
                    val result = callMethod(userName, repositoryName)
                    Assert.assertEquals(expectedResult, result)
                }

                it("should not update cached repositories") {
                    callMethod(userName, repositoryName)

                    verify(cachedSource, never()).setRepositoryCommits(any(), any(), any())
                }
            }
        }

        context("forceRefresh is false") {
            fun callMethod(userName: String, repositoryName: String): BaseResult<RepositoryCommitsResponse> {
                return callMethod(userName, repositoryName, false)
            }

            context("cachedSource is correct") {
                val expectedResult = BaseResult.Ok(commitsList)
                val userName = "testName"
                val repositoryName = "testRepository"

                beforeEachTest {
                    runBlocking {
                        whenever(cachedSource.getRepositoryCommits(userName, repositoryName)).thenReturn(expectedResult)
                    }
                }

                it("should return the cached value") {
                    val result = callMethod(userName, repositoryName)

                    Assert.assertEquals(expectedResult, result)
                }
            }

            context("cachedSource returns error") {
                val remoteResult = BaseResult.Ok(commitsList)
                val userName = "testName"
                val repositoryName = "testRepository"

                beforeEachTest {
                    runBlocking {
                        whenever(remoteSource.getRepositoryCommits(userName, repositoryName)).thenReturn(remoteResult)
                        whenever(cachedSource.getRepositoryCommits(userName, repositoryName)).thenReturn(
                                BaseResult.Error())
                    }
                }

                it("should return the remote value") {
                    val result = callMethod(userName, repositoryName)

                    Assert.assertEquals(remoteResult, result)
                }
            }
        }
    }
})
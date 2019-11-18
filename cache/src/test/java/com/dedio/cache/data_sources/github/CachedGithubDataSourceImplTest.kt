package com.dedio.cache.data_sources.github

import com.dedio.cache.daos.GithubRepositoriesDao
import com.dedio.cache.daos.GithubRepositoryCommitsDao
import com.dedio.cache.entities.GithubRepositories
import com.dedio.cache.entities.GithubRepositoryCommits
import com.dedio.data.exceptions.CacheException
import com.dedio.domain.models.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class CachedGithubDataSourceImplTest: Spek({
    val githubRepositoriesDao by memoized { mock<GithubRepositoriesDao>() }
    val githubRepositoryCommitsDao by memoized { mock<GithubRepositoryCommitsDao>() }

    val subject by memoized { CachedGithubDataSourceImpl(githubRepositoriesDao, githubRepositoryCommitsDao) }

    val repositoryListItem by memoized {
        RepositoryListItem(0, "testId", "testName", "testDescription")
    }
    val repositoryList by memoized {
        val list = RepositoryListResponse()
        list.add(repositoryListItem)
        list
    }

    val commit by memoized { RepositoryCommitsCommit("Test commit message") }
    val commitListItem by memoized { RepositoryCommitsItem("testSha", commit) }
    val commitsList by memoized {
        val list = RepositoryCommitsResponse()
        list.add(commitListItem)
        list
    }

    describe("getRepositories()") {
        fun callMethod(userName: String) = runBlocking {
            subject.getRepositories(userName)
        }

        context("result from database is null") {
            val userName = "testUserName"

            beforeEachTest {
                whenever(githubRepositoriesDao.loadByUserName(userName)).thenReturn(null)
            }

            it("should return CacheException error") {
                val result = callMethod(userName)

                assert((result as BaseResult.Error).exception is CacheException)
            }
        }

        context("result from database in not null") {
            val userName = "testUserName"
            val repositories = GithubRepositories(userName, repositoryList)

            beforeEachTest {
                whenever(githubRepositoriesDao.loadByUserName(userName)).thenReturn(repositories)
            }

            it("should return correct reslut") {
                val expectedResult = BaseResult.Ok(repositories.repositoryList)
                val result = callMethod(userName)

                assertEquals(expectedResult, result)
            }
        }
    }

    describe("setRepositories()") {
        fun callMethod(userName: String, response: RepositoryListResponse) {
            subject.setRepositories(userName, response)
        }

        val userName = "testUser"

        it("should save repositories into dao") {
            callMethod(userName, repositoryList)

            verify(githubRepositoriesDao).insert(GithubRepositories(userName, repositoryList))
        }
    }

    describe("getRepositoryCommits()") {
        fun callMethod(userName: String, repositoryName: String) = runBlocking {
            subject.getRepositoryCommits(userName, repositoryName)
        }

        context("result from database is null") {
            val userName = "testUserName"
            val repositoryName = "testRepositoryName"

            beforeEachTest {
                whenever(githubRepositoryCommitsDao.loadById(userName, repositoryName)).thenReturn(null)
            }

            it("should return CacheException error") {
                val result = callMethod(userName, repositoryName)

                assert((result as BaseResult.Error).exception is CacheException)
            }
        }

        context("result from database in not null") {
            val userName = "testUserName"
            val repositoryName = "testRepositoryName"
            val commits = GithubRepositoryCommits(userName, repositoryName, commitsList)

            beforeEachTest {
                whenever(githubRepositoryCommitsDao.loadById(userName, repositoryName)).thenReturn(commits)
            }

            it("should return correct reslut") {
                val expectedResult = BaseResult.Ok(commits.commitsList)
                val result = callMethod(userName, repositoryName)

                assertEquals(expectedResult, result)
            }
        }
    }

    describe("setRepositoryCommits") {
        fun callMethod(userName: String, repositoryName: String, response: RepositoryCommitsResponse) {
            subject.setRepositoryCommits(userName, repositoryName, response)
        }

        val userName = "testUser"
        val repositoryName = "testRepositoryName"

        it("should save repositories into dao") {
            callMethod(userName, repositoryName, commitsList)

            verify(githubRepositoryCommitsDao).insert(GithubRepositoryCommits(userName, repositoryName, commitsList))
        }
    }
})
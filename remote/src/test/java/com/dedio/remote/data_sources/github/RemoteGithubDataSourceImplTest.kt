package com.dedio.remote.data_sources.github

import com.dedio.remote.net.GithubApi
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class RemoteGithubDataSourceImplTest : Spek({

    val githubApi by memoized { mock<GithubApi>() }

    val subject by memoized { RemoteGithubDataSourceImpl(githubApi) }

    describe("getRepositories()") {
        fun callMethod(userName: String) = runBlocking {
            subject.getRepositories(userName)
        }

        val userName = "testUserName"

        it("should call correct api method") {
            callMethod(userName)

            runBlocking { verify(githubApi).getUserRepos(userName) }
        }
    }

    describe("getRepositoryCommits()") {
        fun callMethod(userName: String, repositoryName: String) = runBlocking {
            subject.getRepositoryCommits(userName, repositoryName)
        }

        val userName = "testUserName"
        val repositoryName = "testRepositoryName"

        it("should call correct api method") {
            callMethod(userName, repositoryName)

            runBlocking { verify(githubApi).getReposCommits(userName, repositoryName) }
        }
    }
})
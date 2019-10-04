package com.dedio.remote.net

import com.dedio.domain.models.RepositoryCommitsResponse
import com.dedio.domain.models.RepositoryListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{userName}/repos")
    suspend fun getUserRepos(@Path("userName") userName: String): Response<RepositoryListResponse>

    @GET("repos/{userName}/{repoName}/commits")
    suspend fun getReposCommits(@Path("userName") userName: String, @Path("repoName") repoName: String): Response<RepositoryCommitsResponse>
}
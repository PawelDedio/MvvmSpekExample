package com.dedio.remote.net

import com.google.gson.Gson
import javax.inject.Inject

const val GITHUB_API_TIMEOUT = 30.toLong()

class GithubRestApiProvider constructor(endpoint: String, gson: Gson) :
    BaseRetrofitApiProvider<GithubApi>(endpoint, GITHUB_API_TIMEOUT, GithubApi::class.java, gson)
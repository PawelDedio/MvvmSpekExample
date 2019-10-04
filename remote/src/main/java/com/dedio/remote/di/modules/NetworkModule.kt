package com.dedio.remote.di.modules

import com.dedio.remote.net.GithubRestApiProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

const val GITHUB_API_ENDPOINT = "https://api.github.com/"

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesGithubRestApiProvider(gson: Gson) = GithubRestApiProvider(GITHUB_API_ENDPOINT, gson)

    @Singleton
    @Provides
    fun provideGithubApi(provider: GithubRestApiProvider) = provider.getApi()
}
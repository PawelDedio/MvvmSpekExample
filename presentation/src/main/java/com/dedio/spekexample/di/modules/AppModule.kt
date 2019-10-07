package com.dedio.spekexample.di.modules

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.dedio.cache.data_sources.github.CachedGithubDataSourceImpl
import com.dedio.data.data_sources.github.CachedGithubDataSource
import com.dedio.data.data_sources.github.RemoteGithubDataSource
import com.dedio.data.repositories.GithubRepositoriesRepositoryImpl
import com.dedio.data.utils.DispatcherProviderImpl
import com.dedio.data.utils.SerializationHelperImpl
import com.dedio.data.utils.ValidationHelperImpl
import com.dedio.domain.repositories.GithubRepositoriesRepository
import com.dedio.domain.utils.DispatcherProvider
import com.dedio.domain.utils.SerializationHelper
import com.dedio.domain.utils.ValidationHelper
import com.dedio.remote.data_sources.github.RemoteGithubDataSourceImpl
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.util.KeyboardHelper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: MainApplication) {

    @Provides
    @Singleton
    fun provideContext() = context.baseContext

    @Provides
    @Singleton
    fun provideMainApplication() = context

    @Provides
    @Singleton
    fun provideGithubRepositoriesRepository(impl: GithubRepositoriesRepositoryImpl) = impl as GithubRepositoriesRepository

    @Provides
    @Singleton
    fun provideCachedGithubDataSource(impl: CachedGithubDataSourceImpl) = impl as CachedGithubDataSource

    @Provides
    @Singleton
    fun provideRemoteGithubDataSource(impl: RemoteGithubDataSourceImpl) = impl as RemoteGithubDataSource

    @Provides
    @Singleton
    fun providesGson() = Gson()

    @Provides
    @Singleton
    fun providesDispatcherProvider(impl: DispatcherProviderImpl) = impl as DispatcherProvider

    @Provides
    @Singleton
    fun providesSerializationHelper(impl: SerializationHelperImpl) = impl as SerializationHelper

    @Provides
    @Singleton
    fun providesValidationHelper(impl: ValidationHelperImpl) = impl as ValidationHelper

    @Provides
    @Singleton
    fun provideInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    @Provides
    @Singleton
    fun provideKeyboardHelper(imm: InputMethodManager): KeyboardHelper {
        return KeyboardHelper(imm)
    }
}
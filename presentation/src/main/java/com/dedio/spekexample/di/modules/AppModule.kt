package com.dedio.spekexample.di.modules

import com.dedio.spekexample.MainApplication
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
}
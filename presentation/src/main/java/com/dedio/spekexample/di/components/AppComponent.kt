package com.dedio.spekexample.di.components

import android.content.Context
import com.dedio.cache.di.modules.DatabaseModule
import com.dedio.remote.di.modules.NetworkModule
import com.dedio.spekexample.di.modules.ActivityModule
import com.dedio.spekexample.di.modules.AppModule
import com.dedio.spekexample.di.modules.ViewModelsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelsModule::class, NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun plus(module: ActivityModule): ActivityComponent
    fun context(): Context
}
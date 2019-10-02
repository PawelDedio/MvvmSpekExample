package com.dedio.spekexample.di.modules

import android.app.Activity
import com.dedio.spekexample.base.BaseActivity
import com.dedio.spekexample.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @ActivityScope
    internal fun provideBaseActivity() = activity

    @Provides
    @ActivityScope
    internal fun provideActivity() = activity as Activity
}
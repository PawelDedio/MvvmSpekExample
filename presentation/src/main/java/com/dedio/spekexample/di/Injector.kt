package com.dedio.spekexample.di

import android.content.Context
import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.base.BaseActivity
import com.dedio.spekexample.di.components.ActivityComponent
import com.dedio.spekexample.di.components.AppComponent
import com.dedio.spekexample.di.components.DaggerAppComponent
import com.dedio.spekexample.di.modules.ActivityModule
import com.dedio.spekexample.di.modules.AppModule

class Injector private constructor() {

    var appComponent: AppComponent? = null
        private set

    var activityComponent: ActivityComponent? = null
        private set

    private fun initialize(context: Context) {
        when(context) {
            is MainApplication -> {
                appComponent = appComponent ?: createAppComponent(context)
            }
            is BaseActivity -> {
                appComponent = appComponent ?: createAppComponent(context)
                activityComponent = createActivityComponent(context)
            }
        }
    }

    private fun createAppComponent(application: MainApplication): AppComponent {
        return DaggerAppComponent.builder().run {
            appModule(AppModule(application))
            build()
        }
    }

    private fun createAppComponent(activity: BaseActivity): AppComponent {
        val application = activity.application as MainApplication
        return createAppComponent(application)
    }

    private fun createActivityComponent(activity: BaseActivity): ActivityComponent {
        return appComponent!!.plus(ActivityModule(activity))
    }

    companion object {
        private var instance: Injector? = null

        fun getInstance(context: Context): Injector {
            if(instance == null) {
                instance = Injector()
            }

            instance!!.initialize(context)

            return instance!!
        }
    }
}
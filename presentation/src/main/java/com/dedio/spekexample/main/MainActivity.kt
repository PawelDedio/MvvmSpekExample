package com.dedio.spekexample.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.dedio.spekexample.R
import com.dedio.spekexample.base.BaseActivity
import com.dedio.spekexample.common.features.HasLoader
import com.dedio.spekexample.di.components.ActivityComponent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), HasLoader {

    override fun makeInject(component: ActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showLoading() {
        mainLoadingOverlayView.setVisibilityWithAnimation(View.VISIBLE)
    }

    override fun hideLoading() {
        mainLoadingOverlayView.setVisibilityWithAnimation(View.GONE)
    }

    override fun observeLoading(isLoading: LiveData<Boolean>) {
        isLoading.observe {
            if(it) showLoading() else hideLoading()
        }
    }
}

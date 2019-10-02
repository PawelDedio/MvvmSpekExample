package com.dedio.spekexample.common.features

import androidx.lifecycle.LiveData

interface HasLoader {

    fun showLoading()

    fun hideLoading()

    fun observeLoading(isLoading: LiveData<Boolean>)
}
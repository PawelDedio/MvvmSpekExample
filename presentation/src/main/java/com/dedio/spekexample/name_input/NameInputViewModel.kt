package com.dedio.spekexample.name_input

import com.dedio.spekexample.MainApplication
import com.dedio.spekexample.base.BaseViewModel
import com.dedio.spekexample.util.ResourceRepository
import com.dedio.spekexample.util.delegators.MutableLiveDataProvider
import javax.inject.Inject

class NameInputViewModel @Inject constructor(application: MainApplication, resourceRepository: ResourceRepository) :
    BaseViewModel(application, resourceRepository) {

    val userName by MutableLiveDataProvider<String>()

    fun onSearchClick() {

    }
}
package com.dedio.spekexample.di.modules

import androidx.lifecycle.ViewModelProvider
import com.dedio.spekexample.base.BaseViewModel
import com.dedio.spekexample.di.annotations.ViewModelKey
import com.dedio.spekexample.name_input.NameInputViewModel
import com.dedio.spekexample.user_repositories.UserRepositoriesViewModel
import com.dedio.spekexample.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NameInputViewModel::class)
    abstract fun bindNameInputViewModel(viewModel: NameInputViewModel): BaseViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserRepositoriesViewModel::class)
    abstract fun bindUserRepositoriesViewModel(viewModel: UserRepositoriesViewModel): BaseViewModel
}
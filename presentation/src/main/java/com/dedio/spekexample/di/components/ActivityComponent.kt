package com.dedio.spekexample.di.components

import com.dedio.spekexample.main.MainActivity
import com.dedio.spekexample.di.modules.ActivityModule
import com.dedio.spekexample.di.scopes.ActivityScope
import com.dedio.spekexample.name_input.NameInputFragment
import com.dedio.spekexample.repository_commits.RepositoryCommitsFragment
import com.dedio.spekexample.user_repositories.UserRepositoriesFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: NameInputFragment)

    fun inject(fragment: UserRepositoriesFragment)

    fun inject(fragment: RepositoryCommitsFragment)
}
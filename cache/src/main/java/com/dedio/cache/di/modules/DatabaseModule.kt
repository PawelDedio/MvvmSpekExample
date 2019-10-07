package com.dedio.cache.di.modules

import android.content.Context
import androidx.room.Room
import com.dedio.cache.DATABASE_FILE_NAME
import com.dedio.cache.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule() {

    @Provides
    @Singleton
    fun providesDatabase(context: Context) = Room.databaseBuilder(context, Database::class.java, DATABASE_FILE_NAME)
        .build()

    @Provides
    @Singleton
    fun providesGithubRepositoriesDao(database: Database) = database.githubRepositoriesDao()

    @Provides
    @Singleton
    fun providesGithubRepositoryCommitsDao(database: Database) = database.githubRepositoryCommitsDao()
}
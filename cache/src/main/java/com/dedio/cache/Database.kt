package com.dedio.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dedio.cache.daos.GithubRepositoriesDao
import com.dedio.cache.daos.GithubRepositoryCommitsDao
import com.dedio.cache.entities.GithubRepositories
import com.dedio.cache.entities.GithubRepositoryCommits
import com.dedio.cache.utils.RepositoryCommitsListTypeConverter
import com.dedio.cache.utils.RepositoryListTypeConverter

const val DATABASE_FILE_NAME = "MvvmSpekExample.db"

@Database(entities = [GithubRepositories::class, GithubRepositoryCommits::class], version = 2)
@TypeConverters(RepositoryListTypeConverter::class, RepositoryCommitsListTypeConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun githubRepositoriesDao(): GithubRepositoriesDao

    abstract fun githubRepositoryCommitsDao(): GithubRepositoryCommitsDao
}
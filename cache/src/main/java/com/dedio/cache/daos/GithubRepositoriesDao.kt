package com.dedio.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dedio.cache.entities.GITHUB_REPOSITORIES_TABLE
import com.dedio.cache.entities.GithubRepositories
import com.dedio.cache.entities.REPOSITORIES_USER_NAME_COLUMN

@Dao
interface GithubRepositoriesDao {

    @Query("SELECT * FROM $GITHUB_REPOSITORIES_TABLE where $REPOSITORIES_USER_NAME_COLUMN = :userName LIMIT 1")
    fun loadByUserName(userName: String): GithubRepositories?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repositories: GithubRepositories)
}
package com.dedio.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dedio.cache.entities.COMMITS_ID_COLUMN
import com.dedio.cache.entities.GITHUB_REPOSITORY_COMMITS_TABLE
import com.dedio.cache.entities.GithubRepositoryCommits
import com.dedio.cache.entities.generateRepositoryCommitsId

@Dao
interface GithubRepositoryCommitsDao {

    @Query("SELECT * FROM $GITHUB_REPOSITORY_COMMITS_TABLE where $COMMITS_ID_COLUMN = :userName + :repositoryName LIMIT 1")
    fun loadById(userName: String, repositoryName: String): GithubRepositoryCommits?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commits: GithubRepositoryCommits)
}
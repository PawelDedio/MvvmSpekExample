package com.dedio.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dedio.domain.models.RepositoryCommitsResponse

const val GITHUB_REPOSITORY_COMMITS_TABLE = "github_repository_commits"
const val COMMITS_ID_COLUMN = "id"
const val COMMITS_USER_NAME_COLUMN = "user_name"
const val COMMITS_REPOSITORY_NAME_COLUMN = "repository_name"
const val COMMITS_COMMIT_LIST_COLUMN = "commit_list"

@Entity(tableName = GITHUB_REPOSITORY_COMMITS_TABLE)
data class GithubRepositoryCommits(@ColumnInfo(
        name = COMMITS_USER_NAME_COLUMN) val userName: String, @ColumnInfo(
        name = COMMITS_REPOSITORY_NAME_COLUMN) val repositoryName: String, @ColumnInfo(
        name = COMMITS_COMMIT_LIST_COLUMN) val commitsList: RepositoryCommitsResponse, @PrimaryKey @ColumnInfo(
        name = COMMITS_ID_COLUMN) val id: String = generateRepositoryCommitsId(userName,
        repositoryName))

fun generateRepositoryCommitsId(userName: String,
                                repositoryName: String) = userName + repositoryName
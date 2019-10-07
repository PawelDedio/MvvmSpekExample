package com.dedio.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dedio.domain.models.RepositoryListResponse

const val GITHUB_REPOSITORIES_TABLE = "github_repositories"
const val REPOSITORIES_USER_NAME_COLUMN = "user_name"
const val REPOSITORIES_REPOSITORY_LIST_COLUMN = "repository_list"

@Entity(tableName = GITHUB_REPOSITORIES_TABLE)
data class GithubRepositories(@PrimaryKey @ColumnInfo(
        name = REPOSITORIES_USER_NAME_COLUMN) val userName: String, @ColumnInfo(
        name = REPOSITORIES_REPOSITORY_LIST_COLUMN) val repositoryList: RepositoryListResponse)
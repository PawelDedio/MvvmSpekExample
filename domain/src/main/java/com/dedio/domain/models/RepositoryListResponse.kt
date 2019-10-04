package com.dedio.domain.models

class RepositoryListResponse : ArrayList<RepositoryListItem>()

data class RepositoryListItem(val id: Int, val noteId: String, val name: String, val description: String)
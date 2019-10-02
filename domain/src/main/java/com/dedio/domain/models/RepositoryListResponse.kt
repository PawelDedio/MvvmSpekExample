package com.dedio.domain.models

class RepositoryListResponse : ArrayList<RepositoryListResponse>()

data class RepositoryListItem(val id: Int, val noteId: String, val name: String, val description: String)
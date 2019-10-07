package com.dedio.domain.models

import com.google.gson.annotations.SerializedName

class RepositoryListResponse : ArrayList<RepositoryListItem>()

data class RepositoryListItem(@SerializedName("id") val id: Int, @SerializedName(
        "node_id") val nodeId: String, @SerializedName("name") val name: String, @SerializedName(
        "description") val description: String)
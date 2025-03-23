package com.example.haveneraowner.data.models.response

data class OwnerServiceResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ResultItem>
)

data class ResultItem(
    val id: Int,
    val owner: Owner,
    val name: String,
    val description: String,
    val price: String
)
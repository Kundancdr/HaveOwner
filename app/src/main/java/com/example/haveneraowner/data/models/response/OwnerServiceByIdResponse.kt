package com.example.haveneraowner.data.models.response

data class OwnerServiceByIdResponse(
    val id: Int,
    val owner: Owner,
    val name: String,
    val description: String,
    val price: String
)

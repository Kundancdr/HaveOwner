package com.example.haveneraowner.data.models.request

data class CreateOwnerServiceRequest(
    val name: String,
    val description: String,
    val price: Int
)

package com.example.haveneraowner.data.models.response

data class CreateOwnerServiceResponse(
    val message: String,
    val data: ServiceData
)

data class ServiceData(
    val id: Int,
    val owner: Owner,
    val name: String,
    val description: String,
    val price: String
)
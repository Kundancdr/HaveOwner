package com.swastik.haveneraowner.data.models.response

data class GetServicesById(
    val description: String,
    val id: Int,
    val name: String,
    val owner: Owner,
    val price: String
)
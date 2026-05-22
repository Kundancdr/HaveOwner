package com.swastik.haveneraowner.data.models.request

data class UpdateOwnerServiceRequest(
    val name: String,
    val description: String,
    val price: Int
)

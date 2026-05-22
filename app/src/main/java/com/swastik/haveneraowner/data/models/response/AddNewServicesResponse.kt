package com.swastik.haveneraowner.data.models.response

data class AddNewServicesResponse(
    val `data`: Data,
    val message: String
)

data class Data(
    val description: String,
    val id: Int,
    val name: String,
    val owner: Owner,
    val price: String
)
package com.example.haveneraowner.data.models.response

class GetAllCategoriesResponse : ArrayList<GetAllCategoriesResponseItem>()

data class GetAllCategoriesResponseItem(
    val description: String,
    val id: Int,
    val name: String
)
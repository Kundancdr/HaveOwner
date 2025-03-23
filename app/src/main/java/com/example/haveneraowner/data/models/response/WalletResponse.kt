package com.example.haveneraowner.data.models.response

data class WalletResponse(
    val id: Int,
    val user: User,
    val balance: String,
    val total_earning: String
)
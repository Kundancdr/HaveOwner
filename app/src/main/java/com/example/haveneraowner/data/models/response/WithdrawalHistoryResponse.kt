package com.example.haveneraowner.data.models.response


data class WithdrawalHistoryResponse(
    val id: Int,
    val wallet: Wallet,
    val amount: String,
    val status: String,
    val requested_at: String,
    val processed_at: String
)

data class Wallet(
    val id: Int,
    val user: User,
    val balance: String,
    val total_earning: String
)
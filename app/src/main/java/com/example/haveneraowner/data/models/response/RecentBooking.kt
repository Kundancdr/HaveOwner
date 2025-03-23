package com.example.haveneraowner.data.models.response



data class RecentBooking(
    val id: Int,
    val room: Room,
    val user: User
)

data class User(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val email: String
)
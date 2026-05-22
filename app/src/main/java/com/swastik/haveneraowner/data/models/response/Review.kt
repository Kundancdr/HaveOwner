package com.swastik.haveneraowner.data.models.response



data class Room(
    val id: Int,
    val room_name: String
)

data class Review(
    val id: Int,
    val user: User,
    val room: Room,
    val rating: Int,
    val comment: String,
    val created_at: String
)

data class ReviewResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Review>
)
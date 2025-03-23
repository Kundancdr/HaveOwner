package com.example.haveneraowner.data.models

import com.example.haveneraowner.data.models.response.Owner
import com.example.haveneraowner.data.models.response.Review

data class CreateOwnerRoomResponse(
    val id: Int,
    val owner: Owner,
    val category: Category,
    val room_name: String,
    val description: String,
    val rules: String,
    val price_per_night: String,
    val price_not_per_night: String,
    val tax: String,
    val room_type: String,
    val capacity: Long,
    val no_of_room: Long,
    val latitude: String,
    val longitude: String,
    val near_by: String,
    val address: String,
    val address_line2: String,
    val city: String,
    val state: String,
    val postal_code: String,
    val country: String,
    val available_from: String,
    val available_to: String,
    val facilities: List<Facility>,
    val services: List<Service>,
    val main_image: String,
    val additional_images: List<AdditionalImage>,
    val status: String,
    val reviews: List<Review>
)

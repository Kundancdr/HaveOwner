package com.swastik.haveneraowner.data.models

import com.swastik.haveneraowner.data.models.response.Owner
import com.swastik.haveneraowner.data.models.response.Review

data class OwnerRoomResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Rooms>
)

data class Rooms(
    val id: Int,
    val owner: Owner,
    val category: Category,
    val room_name: String,
    val room_for: String,
    val description: String,
    val rules: String,
    val price_per_night: String,
    val price_not_per_night: String,
    val tax: String,
    val room_type: String,
    val capacity: Int,
    val no_of_room: Int,
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
    val available_to: String?,
    val facilities: List<Facility>,
    val services: List<Service>,
    val main_image: String,
    val additional_images: List<AdditionalImage>,
    val status: String,
    val reviews: List<Review>
)

data class Category(
    val id: Int,
    val name: String,
    val description: String
)

data class Facility(
    val id: Int,
    val name: String,
    val description: String,
    val icon: String
)

data class Service(
    val id: Int,
    val owner: Owner,
    val name: String,
    val description: String,
    val price: String
)

data class AdditionalImage(
    val id: Int,
    val image: String,
    val description: String?
)

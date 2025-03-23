package com.example.haveneraowner.data.models

data class CreateOwnerRoomRequest(
    val category: Categorys?,
    val room_name: String,
    val description: String,
    val rules: String?,
    val price_per_night: Double,
    val price_not_per_night: Double?,
    val tax: Double?,
    val room_type: String?,
    val capacity: Int,
    val no_of_room: Int,
    val latitude: Double,
    val longitude: Double,
    val near_by: String?,
    val address: String?,
    val address_line2: String?,
    val city: String?,
    val state: String?,
    val postal_code: String?,
    val country: String?,
    val available_from: String,
    val available_to: String?,
    val facilities: List<Facilitys>,
    val services: List<Services>,
    val main_image: String?,
    val additional_images: List<String>,
    val status: String,
    val created_at: String
)

data class Categorys(val id: Int, val name: String)
data class Facilitys(val id: Int, val name: String)
data class Services(val id: Int, val name: String, val price: Double)

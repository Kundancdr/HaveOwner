package com.example.haveneraowner.data.models

import com.example.haveneraowner.data.models.response.CategoryResponse
import com.example.haveneraowner.data.models.response.FacilityResponse
import com.example.haveneraowner.data.models.response.OwnerServiceResponse

data class CreateOwnerRoomRequest(
    val category: Int,
    val room_for : Int,
    val room_name: String,
    val description: String,
    //val rules: String?,
    val price_per_night: Double,
    val price_not_per_night: Double?,
    val tax: Double?,
    val capacity: Int,
    //val room_type: String?,
    val no_of_room: Int,
    val latitude: Double,
    val longitude: Double,
    //val near_by: String?,
    val address: String?,
   // val address_line2: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val postal_code: String?,
    val available_from: String,
    //val available_to: String?,
    val facilities: List<Int>,
    val services: List<Int>,
    val status: String,
    val main_image: String?,
    val additional_images: List<String>
)

//data class Categorys(val id: Int, val name: String)
//data class Facilitys(val id: Int, val name: String)
//data class Services(val id: Int, val name: String, val price: Double)

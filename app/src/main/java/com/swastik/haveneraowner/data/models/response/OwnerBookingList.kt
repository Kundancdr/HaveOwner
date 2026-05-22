package com.swastik.haveneraowner.data.models.response

import com.swastik.haveneraowner.data.models.Rooms
import com.swastik.haveneraowner.data.models.Service


data class OwnerBookingsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Booking>
)

data class Booking(
    val id: Int,
    val room: Rooms,
    val user: User,
    val services: List<Service>,
    val user_name: String,
    val user_email: String,
    val no_of_room: Int,
    val capacity: Int,
    val user_phone: String,
    val check_in_date: String,
    val check_out_date: String,
    val total_amount: String,
    val payment_id: String?
)



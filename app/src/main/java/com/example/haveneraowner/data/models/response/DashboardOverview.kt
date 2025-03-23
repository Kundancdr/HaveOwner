package com.example.haveneraowner.data.models.response

data class DashboardOverview(
    val total_bookings: Int,
    val total_revenue: Double,
    val occupancy_rate: Double
)

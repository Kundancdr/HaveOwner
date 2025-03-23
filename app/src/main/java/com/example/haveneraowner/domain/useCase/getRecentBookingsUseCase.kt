package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.response.DashboardOverview
import com.example.haveneraowner.data.models.response.RecentBooking
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class getRecentBookingsUseCase(private val repo: Repo) {
    suspend fun execute(): Flow<Results<Response<RecentBooking>>> {
        return repo.getRecentBookings()
    }
}
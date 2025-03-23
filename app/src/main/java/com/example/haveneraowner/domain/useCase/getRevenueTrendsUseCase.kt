package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.response.BookingTrends
import com.example.haveneraowner.data.models.response.DashboardOverview
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class getRevenueTrendsUseCase(private val repo: Repo) {
    suspend fun execute(): Flow<Results<Response<BookingTrends>>> {
        return repo.getRevenueTrends()
    }
}
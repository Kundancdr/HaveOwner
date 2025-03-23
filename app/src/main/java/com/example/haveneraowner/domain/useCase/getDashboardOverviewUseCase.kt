package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.response.DashboardOverview
import com.example.haveneraowner.data.models.response.OwnerBookingsResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class getDashboardOverviewUseCase(private val repo: Repo) {
    suspend fun execute(): Flow<Results<Response<DashboardOverview>>> {
        return repo.getDashboardOverview()
    }
}
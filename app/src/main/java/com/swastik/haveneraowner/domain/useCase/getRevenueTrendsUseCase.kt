package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.response.BookingTrends
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class getRevenueTrendsUseCase(private val repo: Repo) {
    suspend fun execute(): Flow<Results<Response<BookingTrends>>> {
        return repo.getRevenueTrends()
    }
}
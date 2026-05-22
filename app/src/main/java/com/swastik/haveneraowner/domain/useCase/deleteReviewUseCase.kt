package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class deleteReviewUseCase(private val repo: Repo) {
    suspend fun execute(reviewId: Int): Flow<Results<Response<Unit>>> {
        return repo.deleteReview(reviewId)
    }
}
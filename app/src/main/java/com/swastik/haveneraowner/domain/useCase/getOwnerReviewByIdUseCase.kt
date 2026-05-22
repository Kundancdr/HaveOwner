package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.response.Review
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class getOwnerReviewByIdUseCase(private val repo: Repo) {
    suspend fun execute(reviewId: Int): Flow<Results<Response<Review>>> {
        return repo.getOwnerReviewById(reviewId)
    }
}
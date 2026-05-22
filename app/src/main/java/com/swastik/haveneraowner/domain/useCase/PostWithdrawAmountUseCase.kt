package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.request.WithdrawRequest
import com.swastik.haveneraowner.data.models.response.WithdrawResponse
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PostWithdrawAmountUseCase(private val repo: Repo) {
    suspend fun execute(request: WithdrawRequest): Flow<Results<Response<WithdrawResponse>>> {
        return repo.postWithdrawAmount(request)
    }
}
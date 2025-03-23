package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.request.WithdrawRequest
import com.example.haveneraowner.data.models.response.WithdrawResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PostWithdrawAmountUseCase(private val repo: Repo) {
    suspend fun execute(request: WithdrawRequest): Flow<Results<Response<WithdrawResponse>>> {
        return repo.postWithdrawAmount(request)
    }
}
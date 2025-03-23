package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.request.CreateOwnerServiceRequest
import com.example.haveneraowner.data.models.response.CreateOwnerServiceResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CreateOwnerServiceUseCase(private val repo: Repo) {
    suspend fun execute(request: CreateOwnerServiceRequest): Flow<Results<Response<CreateOwnerServiceResponse>>> {
        return repo.createOwnerService(request)
    }
}
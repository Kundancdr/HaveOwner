package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.request.UpdateOwnerServiceRequest
import com.example.haveneraowner.data.models.response.UpdateOwnerServiceResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UpdateOwnerServiceUseCase(private val repo: Repo) {
    suspend fun execute(serviceId: Int, request: UpdateOwnerServiceRequest): Flow<Results<Response<UpdateOwnerServiceResponse>>> {
        return repo.updateOwnerService(serviceId, request)
    }
}
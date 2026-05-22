package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.request.UpdateOwnerServiceRequest
import com.swastik.haveneraowner.data.models.response.UpdateOwnerServiceResponse
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UpdateOwnerServiceUseCase(private val repo: Repo) {
    suspend fun execute(serviceId: Int, request: UpdateOwnerServiceRequest): Flow<Results<Response<UpdateOwnerServiceResponse>>> {
        return repo.updateOwnerService(serviceId, request)
    }
}
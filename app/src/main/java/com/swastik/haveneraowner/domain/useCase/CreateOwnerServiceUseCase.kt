package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.request.CreateOwnerServiceRequest
import com.swastik.haveneraowner.data.models.response.CreateOwnerServiceResponse
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CreateOwnerServiceUseCase(private val repo: Repo) {
    suspend fun execute(request: CreateOwnerServiceRequest): Flow<Results<Response<CreateOwnerServiceResponse>>> {
        return repo.createOwnerService(request)
    }
}
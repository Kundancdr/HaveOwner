package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.response.DeleteOwnerServiceResponse
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class DeleteOwnerServiceUseCase(private val repo: Repo) {
    suspend fun execute(serviceId: Int): Flow<Results<Response<DeleteOwnerServiceResponse>>> {
        return repo.deleteOwnerService(serviceId)
    }
}
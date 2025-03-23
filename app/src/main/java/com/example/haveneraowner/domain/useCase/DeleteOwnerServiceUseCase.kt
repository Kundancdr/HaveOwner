package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.response.DeleteOwnerServiceResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class DeleteOwnerServiceUseCase(private val repo: Repo) {
    suspend fun execute(serviceId: Int): Flow<Results<Response<DeleteOwnerServiceResponse>>> {
        return repo.deleteOwnerService(serviceId)
    }
}
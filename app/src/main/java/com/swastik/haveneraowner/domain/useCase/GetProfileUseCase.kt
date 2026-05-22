package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.response.GetProfileResponse
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetProfileUseCase(private val repository: Repo) {
    suspend fun execute(): Flow<Results<Response<GetProfileResponse>>> {
        return repository.getProfile()
    }
}
package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.response.GetProfileResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetProfileUseCase(private val repository: Repo) {
    suspend fun execute(): Flow<Results<Response<GetProfileResponse>>> {
        return repository.getProfile()
    }
}
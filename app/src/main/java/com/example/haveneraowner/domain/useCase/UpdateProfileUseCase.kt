package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.request.UpdateProfileRequest
import com.example.haveneraowner.data.models.response.UpdateProfileResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UpdateProfileUseCase(private val repository: Repo) {
    suspend fun execute(request: UpdateProfileRequest): Flow<Results<Response<UpdateProfileResponse>>> {
        return repository.updateProfile(request)
    }
}
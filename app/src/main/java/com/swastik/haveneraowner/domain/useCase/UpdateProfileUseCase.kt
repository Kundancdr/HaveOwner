package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.request.UpdateProfileRequest
import com.swastik.haveneraowner.data.models.response.UpdateProfileResponse
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UpdateProfileUseCase(private val repository: Repo) {
    suspend fun execute(request: UpdateProfileRequest): Flow<Results<Response<UpdateProfileResponse>>> {
        return repository.updateProfile(request)
    }
}
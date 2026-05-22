package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.CreateOwnerRoomRequest
import com.swastik.haveneraowner.data.models.CreateOwnerRoomResponse
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CreateOwnerRoomUseCase(private val repo: Repo) {
    suspend fun execute(request: CreateOwnerRoomRequest): Flow<Results<Response<CreateOwnerRoomResponse>>> =
        repo.createOwnerRoom(request)
}
package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.CreateOwnerRoomRequest
import com.example.haveneraowner.data.models.CreateOwnerRoomResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CreateOwnerRoomUseCase(private val repo: Repo) {
    suspend fun execute(request: CreateOwnerRoomRequest): Flow<Results<Response<CreateOwnerRoomResponse>>> =
        repo.createOwnerRoom(request)
}
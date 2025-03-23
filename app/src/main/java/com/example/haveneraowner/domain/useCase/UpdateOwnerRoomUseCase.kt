package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.UpdateOwnerRoomRequest
import com.example.haveneraowner.data.models.UpdateOwnerRoomResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UpdateOwnerRoomUseCase(private val repo: Repo) {
    suspend fun execute(roomId: Int, request: UpdateOwnerRoomRequest): Flow<Results<Response<UpdateOwnerRoomResponse>>> =
        repo.updateOwnerRoom(roomId, request)
}
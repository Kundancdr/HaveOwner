package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.UpdateOwnerRoomRequest
import com.swastik.haveneraowner.data.models.UpdateOwnerRoomResponse
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UpdateOwnerRoomUseCase(private val repo: Repo) {
    suspend fun execute(roomId: Int, request: UpdateOwnerRoomRequest): Flow<Results<Response<UpdateOwnerRoomResponse>>> =
        repo.updateOwnerRoom(roomId, request)
}
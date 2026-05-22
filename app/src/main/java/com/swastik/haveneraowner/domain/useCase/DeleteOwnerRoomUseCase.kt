package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.DeleteOwnerRoomResponse
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class DeleteOwnerRoomUseCase(private val repo: Repo) {
    suspend fun execute(roomId: Int): Flow<Results<Response<DeleteOwnerRoomResponse>>> =
        repo.deleteOwnerRoom(roomId)
}
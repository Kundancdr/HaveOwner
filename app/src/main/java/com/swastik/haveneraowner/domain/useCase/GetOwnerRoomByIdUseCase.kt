package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.common.Results
import com.swastik.haveneraowner.data.models.OwnerRoomByIdResponse
import com.swastik.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetOwnerRoomByIdUseCase(private val repo: Repo) {
    suspend fun execute(roomId: Int): Flow<Results<Response<OwnerRoomByIdResponse>>> =
        repo.getOwnerRoomById(roomId)
}
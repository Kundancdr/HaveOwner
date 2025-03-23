package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.common.Results
import com.example.haveneraowner.data.models.OwnerRoomByIdResponse
import com.example.haveneraowner.domain.Repo.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetOwnerRoomByIdUseCase(private val repo: Repo) {
    suspend fun execute(roomId: Int): Flow<Results<Response<OwnerRoomByIdResponse>>> =
        repo.getOwnerRoomById(roomId)
}
package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.data.models.request.RefreshTokenRequest
import com.example.haveneraowner.domain.Repo.Repo

class RefreshTokenUseCase (private val repository: Repo) {
    suspend fun execute(refreshTokenRequest: RefreshTokenRequest) =
        repository.refreshToken(refreshTokenRequest)
}

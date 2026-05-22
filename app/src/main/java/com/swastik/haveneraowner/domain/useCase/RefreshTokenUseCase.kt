package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.data.models.request.RefreshTokenRequest
import com.swastik.haveneraowner.domain.Repo.Repo

class RefreshTokenUseCase (private val repository: Repo) {
    suspend fun execute(refreshTokenRequest: RefreshTokenRequest) =
        repository.refreshToken(refreshTokenRequest)
}

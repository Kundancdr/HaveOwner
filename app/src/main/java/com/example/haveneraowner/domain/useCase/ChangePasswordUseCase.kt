package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.data.models.request.ChangePasswordRequest
import com.example.haveneraowner.domain.Repo.Repo

class ChangePasswordUseCase(private val repo: Repo) {
    suspend fun execute(changePasswordRequest: ChangePasswordRequest) =
        repo.changePassword(changePasswordRequest)
}
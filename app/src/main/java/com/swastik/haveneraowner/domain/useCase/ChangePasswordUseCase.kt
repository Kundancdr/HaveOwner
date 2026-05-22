package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.data.models.request.ChangePasswordRequest
import com.swastik.haveneraowner.domain.Repo.Repo

class ChangePasswordUseCase(private val repo: Repo) {
    suspend fun execute(changePasswordRequest: ChangePasswordRequest) =
        repo.changePassword(changePasswordRequest)
}
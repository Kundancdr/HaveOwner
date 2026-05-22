package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.data.models.request.ForgetPasswordRequest
import com.swastik.haveneraowner.domain.Repo.Repo


class ForgetPasswordUseCase  (private val repo: Repo) {
    suspend fun execute(forgetPasswordRequest: ForgetPasswordRequest) =
        repo.forgetPassword(forgetPasswordRequest)
}
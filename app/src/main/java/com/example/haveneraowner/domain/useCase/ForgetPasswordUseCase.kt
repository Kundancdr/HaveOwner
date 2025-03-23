package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.data.models.request.ForgetPasswordRequest
import com.example.haveneraowner.domain.Repo.Repo


class ForgetPasswordUseCase  (private val repo: Repo) {
    suspend fun execute(forgetPasswordRequest: ForgetPasswordRequest) =
        repo.forgetPassword(forgetPasswordRequest)
}
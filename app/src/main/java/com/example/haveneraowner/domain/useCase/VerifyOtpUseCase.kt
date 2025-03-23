package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.data.models.request.VerifyOtRequestModel
import com.example.haveneraowner.domain.Repo.Repo


class VerifyOtpUseCase (private val repo: Repo) {
    suspend fun verifyOtpUseCase(verifyOtRequestModel: VerifyOtRequestModel) =
        repo.verifyOtp(verifyOtRequestModel)
}
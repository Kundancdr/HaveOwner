package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.data.models.request.VerifyOtRequestModel
import com.swastik.haveneraowner.domain.Repo.Repo


class VerifyOtpUseCase (private val repo: Repo) {
    suspend fun verifyOtpUseCase(verifyOtRequestModel: VerifyOtRequestModel) =
        repo.verifyOtp(verifyOtRequestModel)
}
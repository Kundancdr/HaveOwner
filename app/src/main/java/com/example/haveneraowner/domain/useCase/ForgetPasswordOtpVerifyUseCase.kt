package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.data.models.request.ForgetPassOtpVerifyRequest
import com.example.haveneraowner.domain.Repo.Repo


class ForgetPasswordOtpVerifyUseCase(private val repo: Repo) {
    suspend fun execute(forgetPassOtpVerifyRequest: ForgetPassOtpVerifyRequest) =
        repo.forgetPasswordOtpVerify(forgetPassOtpVerifyRequest)
}
package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.data.models.request.ForgetPassOtpVerifyRequest
import com.swastik.haveneraowner.domain.Repo.Repo


class ForgetPasswordOtpVerifyUseCase(private val repo: Repo) {
    suspend fun execute(forgetPassOtpVerifyRequest: ForgetPassOtpVerifyRequest) =
        repo.forgetPasswordOtpVerify(forgetPassOtpVerifyRequest)
}
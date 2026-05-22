package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.data.models.request.LoginOtRequestModel
import com.swastik.haveneraowner.domain.Repo.Repo


class LoginOtpVerifyUseCase (private val repo: Repo) {
        suspend fun loginOtpVerifyUseCase(loginOtRequestModel: LoginOtRequestModel) =
            repo.loginOtpVerify(loginOtRequestModel)
    }

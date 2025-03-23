package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.data.models.request.LoginOtRequestModel
import com.example.haveneraowner.domain.Repo.Repo


class LoginOtpVerifyUseCase (private val repo: Repo) {
        suspend fun loginOtpVerifyUseCase(loginOtRequestModel: LoginOtRequestModel) =
            repo.loginOtpVerify(loginOtRequestModel)
    }

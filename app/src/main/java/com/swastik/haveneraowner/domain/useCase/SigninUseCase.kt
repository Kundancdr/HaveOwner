package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.data.models.request.SigninRequestModel
import com.swastik.haveneraowner.domain.Repo.Repo


class SigninUseCase (private val repo: Repo) {
    suspend fun signinUseCase(signinRequestModel: SigninRequestModel) =
        repo.login(signinRequestModel)
}
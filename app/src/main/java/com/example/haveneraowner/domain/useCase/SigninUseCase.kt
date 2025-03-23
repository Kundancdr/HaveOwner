package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.data.models.request.SigninRequestModel
import com.example.haveneraowner.domain.Repo.Repo


class SigninUseCase (private val repo: Repo) {
    suspend fun signinUseCase(signinRequestModel: SigninRequestModel) =
        repo.login(signinRequestModel)
}
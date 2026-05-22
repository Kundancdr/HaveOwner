package com.swastik.haveneraowner.domain.useCase

import com.swastik.haveneraowner.domain.Repo.Repo


class LogOutUseCase (private val repo: Repo) {
    suspend fun logOutUseCase() = repo.logOut()
}
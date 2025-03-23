package com.example.haveneraowner.domain.useCase

import com.example.haveneraowner.domain.Repo.Repo


class LogOutUseCase (private val repo: Repo) {
    suspend fun logOutUseCase() = repo.logOut()
}
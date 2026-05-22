package com.swastik.haveneraowner.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class TokenProvider(private val dataStoreManager: DataStoreManager) {

    fun getAccessToken(): String? = runBlocking {
        dataStoreManager.accessTokenFlow.first()
    }

    fun getRefreshToken(): String? = runBlocking {
        dataStoreManager.refreshTokenFlow.first()
    }

    suspend fun updateAccessToken(newToken: String) {
        dataStoreManager.saveAccessToken(newToken)
    }

    suspend fun updateRefreshToken(newToken: String) {
        dataStoreManager.saveRefreshToken(newToken)
    }
}